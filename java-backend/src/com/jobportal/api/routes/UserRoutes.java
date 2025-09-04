package com.jobportal.api.routes;

import com.jobportal.domain.User;
import com.jobportal.domain.Session;
import com.jobportal.repo.UserRepository;
import com.jobportal.api.services.AuthServices;
import com.jobportal.api.exceptions.UnauthorizedException;

import com.google.gson.Gson;

import fi.iki.elonen.NanoHTTPD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRoutes implements RouteHandler {
    private final Gson gson = new Gson();
    private final UserRepository userRepo = new UserRepository();

    /**
     * Handle HTTP requests matching /user endpoint.
     *
     * @param session the HTTP session containing the request data
     * @return NanoHTTPD.Response containing JSON data or error
     * @throws Exception if body parsing or DB operations fail
     */
    @Override
    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) throws Exception {
        NanoHTTPD.Method method = session.getMethod();
        String uri = session.getUri();

        final String endpoint = "/user";
        String subPath = uri.substring(endpoint.length());
        if (!uri.startsWith(endpoint)) return null;

        // GET /user/files
        if (subPath.equals("/files") && method == NanoHTTPD.Method.GET) {
            /// TODO Send snapshot instead of full file
            try {
                Session sessionObj = AuthServices.validateSession(session);

                Map<String, List<String>> decodedQueryParameters = session.getParameters();
                String fileName = decodedQueryParameters.getOrDefault("file", List.of("")).get(0);
                String filePath = "./uploads/" + sessionObj.getUserId() + "/" + fileName;
                java.io.File src = new java.io.File(filePath);

                if (!src.exists() || !src.isFile()) return jsonError("File not found", NanoHTTPD.Response.Status.NOT_FOUND);

                FileInputStream fis = new FileInputStream(src);
                NanoHTTPD.Response response = NanoHTTPD.newChunkedResponse(
                        NanoHTTPD.Response.Status.OK,
                        "application/octet-stream",
                        fis
                );
                response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
                response.addHeader("Access-Control-Allow-Origin", "*");

                return response;
            } catch (UnauthorizedException e) {
                return jsonError(e.getMessage(), e.getStatus());
            }
        }

        // GET /user
        if (method == NanoHTTPD.Method.GET) {
            try {
                Session sessionObj = AuthServices.validateSession(session);

                User user = userRepo.findById(sessionObj.getUserId());

                return json(gson.toJson(user));
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
        res.addHeader("Access-Control-Allow-Origin", "*"); // Enable CORS for all origins
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
