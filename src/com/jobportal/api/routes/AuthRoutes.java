package com.jobportal.api.routes;

import com.jobportal.domain.User;
import com.jobportal.domain.Session;
import com.jobportal.repo.UserRepository;
import com.jobportal.repo.SessionRepository;
import com.jobportal.api.services.AuthServices;
import com.jobportal.api.exceptions.UnauthorizedException;

import com.google.gson.Gson;

import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

public class AuthRoutes implements RouteHandler {
    private final Gson gson = new Gson();
    private final UserRepository userRepo = new UserRepository();
    private final SessionRepository sessionRepo = new SessionRepository();

    /**
     * Handle HTTP requests matching /auth endpoint.
     *
     * @param session the HTTP session containing the request data
     * @return NanoHTTPD.Response containing JSON data or error
     * @throws Exception if body parsing or DB operations fail
     */
    @Override
    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) throws Exception {
        NanoHTTPD.Method method = session.getMethod();
        String uri = session.getUri();

        final String endpoint = "/auth";
        String subPath = uri.substring(endpoint.length());
        if (!uri.startsWith(endpoint)) return null;

        if (subPath.equals("/check_auth_status") && method == NanoHTTPD.Method.GET) {
            try {
                Session sessionObj = AuthServices.validateSession(session);

                User user = userRepo.findById(sessionObj.getUserId());
                if (user == null) {
                    return jsonError("User not found", NanoHTTPD.Response.Status.UNAUTHORIZED);
                }

                return json(gson.toJson(Map.of("id", user.getId(), "username", user.getUsername())));
            } catch (UnauthorizedException e) {
                return jsonError(e.getMessage(), e.getStatus());
            }
        }

        // POST /auth/login
        if (subPath.equals("/login") && method == NanoHTTPD.Method.POST) {
            HashMap<String, String> map = new HashMap<>();
            session.parseBody(map);
            String body = map.get("postData");

            User loginData = gson.fromJson(body, User.class);
            if (loginData.getUsername() == null) return jsonError("Missing username", NanoHTTPD.Response.Status.BAD_REQUEST);
            if (loginData.getHashedPassword() == null) return jsonError("Missing password", NanoHTTPD.Response.Status.BAD_REQUEST);

            User user = userRepo.findByUsernameAndPassword(loginData.getUsername(), loginData.getHashedPassword());
            if (user == null) return jsonError("Invalid credentials", NanoHTTPD.Response.Status.UNAUTHORIZED);

            Session sessionObj = new Session();
            sessionObj.setUserId(user.getId());
            sessionObj.setSessionToken(UUID.randomUUID().toString());
            sessionObj.setCreatedAt(new Date());
            sessionObj.setExpiresAt(new Date((new Date()).getTime() + 24 * 60 * 60 * 1000));
            sessionRepo.insert(sessionObj);

            return json(gson.toJson(sessionObj.getSessionToken()));
        }

        // POST /auth/register
        if (subPath.equals("/register") && method == NanoHTTPD.Method.POST) {
            /// TODO Register funcitonality
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
