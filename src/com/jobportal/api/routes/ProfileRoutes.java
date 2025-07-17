package com.jobportal.api.routes;

import com.jobportal.domain.Profile;
import com.jobportal.domain.Session;
import com.jobportal.repo.ProfileRepository;
import com.jobportal.api.services.AuthServices;
import com.jobportal.api.exceptions.UnauthorizedException;

import com.google.gson.Gson;

import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileRoutes implements RouteHandler {
    private final Gson gson = new Gson();
    private final ProfileRepository profileRepo = new ProfileRepository();

    /**
     * Handle HTTP requests matching /profile endpoint.
     *
     * @param session the HTTP session containing the request data
     * @return NanoHTTPD.Response containing JSON data or error
     * @throws Exception if body parsing or DB operations fail
     */
    @Override
    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) throws Exception {
        NanoHTTPD.Method method = session.getMethod();
        String uri = session.getUri();

        final String endpoint = "/profile";
        String subPath = uri.substring(endpoint.length());
        if (!uri.startsWith(endpoint)) return null;

        // GET /profile
        if (method == NanoHTTPD.Method.GET) {
            try {
                Session sessionObj = AuthServices.validateSession(session);

                Profile profile = profileRepo.findByUserId(sessionObj.getUserId());

                return json(gson.toJson(profile));
            } catch (UnauthorizedException e) {
                return jsonError(e.getMessage(), e.getStatus());
            }
        }

        // POST /profile/upload (UPDATE)
        if (subPath.equals("/upload") && method == NanoHTTPD.Method.POST) {
            try {
                Session sessionObj = AuthServices.validateSession(session);

                Map<String, String> map = new HashMap<>();
                Map<String, List<String>> params = new HashMap<>();
                session.parseBody(map);
                params = session.getParameters();

                String filePath = map.get("file");
                if (filePath == null) return jsonError("No file uploaded", NanoHTTPD.Response.Status.BAD_REQUEST);
                String fileName = null;
                List<String> fileParam = params.get("file");
                if (fileParam != null && !fileParam.isEmpty()) fileName = fileParam.get(0);

                java.io.File src = new java.io.File(filePath);
                java.io.File dest = new java.io.File("./uploads/" + sessionObj.getUserId() + "/" + fileName);
                dest.getParentFile().mkdirs();
                java.nio.file.Files.copy(src.toPath(), dest.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                Profile profile = profileRepo.findByUserId(sessionObj.getUserId());
                java.io.File oldFile = new java.io.File("./uploads/" + sessionObj.getUserId() + "/" + profile.getResume());
                oldFile.delete();

                profileRepo.updateResume(sessionObj.getUserId(), dest.getName());

                return json(gson.toJson(Map.of("message", "File uploaded successfully")));
            } catch (UnauthorizedException e) {
                return jsonError(e.getMessage(), e.getStatus());
            }
        }

        // POST /profile (UPDATE)
        if (method == NanoHTTPD.Method.POST) {
            try {
                Session sessionObj = AuthServices.validateSession(session);

                Map<String, String> map = new HashMap<>();
                session.parseBody(map);
                String body = map.get("postData");

                Profile profile = gson.fromJson(body, Profile.class);

                profileRepo.updateProfile(sessionObj.getUserId(), profile);

                return json(gson.toJson(Map.of("message", "Profile updated successfully")));
            } catch (UnauthorizedException e) {
                return jsonError(e.getMessage(), e.getStatus());
            }
        }

        // If method not supported for this route
        return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.NOT_FOUND,
                "application/json",
                "{\"error\":\"Not Found\"}"
        );
    }

    /**
     * Helper method to create JSON HTTP responses with CORS header.
     *
     * @param data JSON string response body
     * @return NanoHTTPD.Response with JSON content type and CORS enabled
     */
    private NanoHTTPD.Response json(String data) {
        NanoHTTPD.Response res = NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.OK,
                "application/json",
                data
        );
        res.addHeader("Access-Control-Allow-Origin", "*");
        return res;
    }

    /**
     * Helper method to create JSON HTTP error responses with CORS header.
     *
     * @param message Error message to be included in the JSON response body
     * @param status HTTP response status to set for the error
     * @return NanoHTTPD.Response with JSON content type, specified status, and CORS enabled
     */
    private NanoHTTPD.Response jsonError(String message, NanoHTTPD.Response.Status status) {
        NanoHTTPD.Response res = NanoHTTPD.newFixedLengthResponse(
                status,
                "application/json",
                gson.toJson(Map.of("error", message))
        );
        res.addHeader("Access-Control-Allow-Origin", "*");
        return res;
    }
}
