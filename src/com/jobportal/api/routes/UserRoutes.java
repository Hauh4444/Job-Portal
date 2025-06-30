package com.jobportal.api.routes;

import com.google.gson.Gson;
import com.jobportal.domain.User;
import com.jobportal.repo.UserRepository;
import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRoutes implements RouteHandler {
    // Gson instance for JSON serialization/deserialization
    private final Gson gson = new Gson();

    // Repository for interacting with User data in MongoDB
    private final UserRepository repo = new UserRepository();

    /**
     * Handle HTTP requests matching /users endpoint.
     *
     * @param session the HTTP session containing the request data
     * @return NanoHTTPD.Response containing JSON data or error
     * @throws Exception if body parsing or DB operations fail
     */
    @Override
    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) throws Exception {
        NanoHTTPD.Method method = session.getMethod();
        String uri = session.getUri();

        // Only handle requests for "/users"
        if (!"/users".equals(uri)) return null; // Not handled here, let other handlers try

        // GET /users
        if (method == NanoHTTPD.Method.GET) {
            // Retrieve all users from DB and return as JSON array
            List<User> users = repo.findAll();
            return json(gson.toJson(users));
        }

        // POST /users
        if (method == NanoHTTPD.Method.POST) {
            // Parse request body JSON to User object and insert into DB
            HashMap<String, String> map = new HashMap<>();
            session.parseBody(map);
            String body = map.get("postData");
            User user = gson.fromJson(body, User.class);
            repo.insert(user);
            return json("{\"status\":\"ok\"}");
        }

        // DELETE /users
        if (method == NanoHTTPD.Method.DELETE) {
            // Extract 'id' query parameter and delete matching user
            Map<String, List<String>> params = session.getParameters();
            List<String> ids = params.get("id");
            if (ids == null || ids.isEmpty()) {
                return NanoHTTPD.newFixedLengthResponse(
                        NanoHTTPD.Response.Status.BAD_REQUEST,
                        "application/json",
                        "{\"error\":\"Missing id parameter\"}"
                );
            }
            String id = ids.get(0);
            repo.delete(id);
            return json("{\"status\":\"deleted\"}");
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
}
