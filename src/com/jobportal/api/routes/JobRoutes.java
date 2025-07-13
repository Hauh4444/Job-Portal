package com.jobportal.api.routes;

import com.jobportal.domain.Job;
import com.jobportal.repo.JobRepository;

import com.google.gson.Gson;

import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobRoutes implements RouteHandler {
    private final Gson gson = new Gson();
    private final JobRepository jobRepo = new JobRepository();

    /**
     * Handle HTTP requests matching /jobs endpoint.
     *
     * @param session the HTTP session containing the request data
     * @return NanoHTTPD.Response containing JSON data or error
     * @throws Exception if body parsing or DB operations fail
     */
    @Override
    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) throws Exception {
        NanoHTTPD.Method method = session.getMethod();
        String uri = session.getUri();

        final String endpoint = "/jobs";

        if (!endpoint.equals(uri)) return null;

        // GET /jobs
        if (method == NanoHTTPD.Method.GET) {
            List<Job> jobs = jobRepo.findAll();

            return json(gson.toJson(jobs));
        }

        // POST /jobs
        if (method == NanoHTTPD.Method.POST) {
            HashMap<String, String> map = new HashMap<>();
            session.parseBody(map);
            String body = map.get("postData");
            Job job = gson.fromJson(body, Job.class);

            jobRepo.insert(job);

            return json("{\"status\":\"ok\"}");
        }

        // DELETE /jobs
        if (method == NanoHTTPD.Method.DELETE) {
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

            jobRepo.delete(id);

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
