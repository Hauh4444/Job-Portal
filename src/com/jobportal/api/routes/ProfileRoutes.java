package com.jobportal.api.routes;

import com.jobportal.domain.Profile;
import com.jobportal.domain.Session;
import com.jobportal.repo.ProfileRepository;
import com.jobportal.repo.SessionRepository;

import com.google.gson.Gson;

import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileRoutes implements RouteHandler {
    private final Gson gson = new Gson();
    private final ProfileRepository profileRepo = new ProfileRepository();;
    private final SessionRepository sessionRepo = new SessionRepository();

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
            String authHeader = session.getHeaders().get("authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return jsonError("Missing or invalid Authorization header", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }
            String sessionToken = authHeader.substring("Bearer ".length());
            Session sessionObj = sessionRepo.findBySessionToken(sessionToken);
            if (sessionObj == null) {
                return jsonError("Invalid session token", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }

            Profile profile = profileRepo.findByUserId(sessionObj.getUserId());

            return json(gson.toJson(profile));
        }

        // PUT /profile/upload
        if (subPath.equals("/upload") && method == NanoHTTPD.Method.POST) {
            /// TODO Delete old file
            String authHeader = session.getHeaders().get("authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return jsonError("Missing or invalid Authorization header", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }
            String sessionToken = authHeader.substring("Bearer ".length());
            Session sessionObj = sessionRepo.findBySessionToken(sessionToken);
            if (sessionObj == null) {
                return jsonError("Invalid session token", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }

            Map<String, String> map = new HashMap<>();
            Map<String, List<String>> params = new HashMap<>();
            session.parseBody(map);
            params = session.getParameters();
            String tempFilePath = map.get("file");
            if (tempFilePath == null) {
                return jsonError("No file uploaded", NanoHTTPD.Response.Status.BAD_REQUEST);
            }
            String fileName = null;
            List<String> fileParam = params.get("file");
            if (fileParam != null && !fileParam.isEmpty()) {
                fileName = fileParam.get(0);
            }

            java.io.File src = new java.io.File(tempFilePath);
            java.io.File dest = new java.io.File("./uploads/" + sessionObj.getUserId() + "/" + fileName);
            dest.getParentFile().mkdirs();
            java.nio.file.Files.copy(src.toPath(), dest.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            profileRepo.updateResume(sessionObj.getUserId(), dest.getName());

            return json(gson.toJson(Map.of("message", "File uploaded successfully")));
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
