package com.jobportal.api.routes;

import com.google.gson.Gson;
import com.jobportal.domain.User;
import com.jobportal.domain.Session;
import com.jobportal.repo.UserRepository;
import com.jobportal.repo.SessionRepository;
import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

public class AuthRoutes implements RouteHandler {
    // Gson instance for JSON serialization/deserialization
    private final Gson gson = new Gson();

    // Repository for interacting with User data in MongoDB
    private final UserRepository userRepo = new UserRepository();
    // Repository for interacting with Session data in MongoDB
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

        // Only handle requests for "/auth"
        if (!uri.startsWith("/auth")) return null; // Not handled here, let other handlers try

        String subPath = uri.substring("/auth".length());

        if ("/check_auth_status".equals(subPath) && method == NanoHTTPD.Method.GET) {
            String authHeader = session.getHeaders().get("authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return jsonError("Missing or invalid Authorization header", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }
            String sessionToken = authHeader.substring("Bearer ".length());

            Session sessionObj = sessionRepo.findBySessionToken(sessionToken);
            if (sessionObj == null) {
                return jsonError("Invalid session token", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }

            Date now = new Date();
            if (sessionObj.getExpiresAt().before(now)) {
                return jsonError("Session expired", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }

            User user = userRepo.findById(sessionObj.getUserId());
            if (user == null) {
                return jsonError("User not found", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }

            return json(gson.toJson(Map.of("id", user.getId(), "username", user.getUsername())));
        }

        if ("/login".equals(subPath) && method == NanoHTTPD.Method.POST) {
            HashMap<String, String> map = new HashMap<>();
            session.parseBody(map);
            String body = map.get("postData");

            User postData = gson.fromJson(body, User.class);
            if (postData.getUsername() == null || postData.getHashedPassword() == null) {
                return jsonError("Missing username or hashedPassword", NanoHTTPD.Response.Status.BAD_REQUEST);
            }

            User user = userRepo.findByUsernameAndPassword(postData.getUsername(), postData.getHashedPassword());
            if (user == null) {
                return jsonError("Invalid credentials", NanoHTTPD.Response.Status.UNAUTHORIZED);
            }

            Session sessionObj = new Session();
            String sessionToken = UUID.randomUUID().toString();
            Date now = new Date();

            sessionObj.setUserId(user.getId());
            sessionObj.setSessionToken(sessionToken);
            sessionObj.setCreatedAt(now);
            sessionObj.setExpiresAt(new Date(now.getTime() + 24 * 60 * 60 * 1000));

            sessionRepo.insert(sessionObj);

            return json(gson.toJson(sessionToken));
        }

        if ("/register".equals(subPath) && method == NanoHTTPD.Method.POST) {

        }

        // If method not supported for this route
        return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.NOT_FOUND,
                "application/json",
                "{\"error\":\"Not Found\"}"
        );
    }

    private NanoHTTPD.Response json(String data) {
        NanoHTTPD.Response res = NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.OK,
                "application/json",
                data
        );
        res.addHeader("Access-Control-Allow-Origin", "*"); // Enable CORS for all origins
        return res;
    }

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
