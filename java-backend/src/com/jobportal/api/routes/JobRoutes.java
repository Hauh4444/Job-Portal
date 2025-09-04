package com.jobportal.api.routes;

import com.jobportal.domain.Job;
import com.jobportal.domain.Session;
import com.jobportal.domain.Application;
import com.jobportal.repo.JobRepository;
import com.jobportal.repo.ApplicationRepository;
import com.jobportal.api.services.AuthServices;
import com.jobportal.api.exceptions.UnauthorizedException;

import com.google.gson.Gson;

import org.bson.types.ObjectId;

import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobRoutes implements RouteHandler {
    private final Gson gson = new Gson();
    private final JobRepository jobRepo = new JobRepository();
    private final ApplicationRepository applicationRepo = new ApplicationRepository();

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
        String subPath = uri.substring(endpoint.length());
        if (!uri.startsWith(endpoint)) return null;

        // GET /jobs/applied
        if (subPath.equals("/applied") && method == NanoHTTPD.Method.GET) {
            try {
                Session sessionObj = AuthServices.validateSession(session);

                Map<String, List<String>> params = session.getParameters();
                // Extract parts of the ObjectId from params
                String counter = params.get("jobId[counter]").get(0);
                String timestamp = params.get("jobId[timestamp]").get(0);
                String randomValue1 = params.get("jobId[randomValue1]").get(0);
                String randomValue2 = params.get("jobId[randomValue2]").get(0);
                // Convert each to hex strings padded to correct length:
                String timestampHex = Long.toHexString(Long.parseLong(timestamp));
                String counterHex = Long.toHexString(Long.parseLong(counter));
                String randomValue1Hex = Long.toHexString(Long.parseLong(randomValue1));
                String randomValue2Hex = Long.toHexString(Long.parseLong(randomValue2));
                // Pad hex strings to required lengths:
                timestampHex = String.format("%8s", timestampHex).replace(' ', '0');
                counterHex = String.format("%6s", counterHex).replace(' ', '0');
                randomValue1Hex = String.format("%4s", randomValue1Hex).replace(' ', '0');
                randomValue2Hex = String.format("%4s", randomValue2Hex).replace(' ', '0');
                // Rebuild the ObjectId from parts
                String jobIdString = timestampHex + randomValue1Hex + randomValue2Hex + counterHex;
                ObjectId jobId = new ObjectId(jobIdString);

                boolean applied = applicationRepo.findByJobIdAndUserId(jobId, sessionObj.getUserId());

                return json(gson.toJson(Map.of("applied", applied)));
            } catch (UnauthorizedException e) {
                return jsonError(e.getMessage(), e.getStatus());
            }
        }

        // GET /jobs
        if (method == NanoHTTPD.Method.GET) {
            List<Job> jobs = jobRepo.findAll();

            return json(gson.toJson(jobs));
        }

        // POST /jobs/apply
        if (subPath.equals("/apply") && method == NanoHTTPD.Method.POST) {
            try {
                Session sessionObj = AuthServices.validateSession(session);

                Map<String, String> map = new HashMap<>();
                session.parseBody(map);
                String body = map.get("postData");

                Application application = gson.fromJson(body, Application.class);
                application.setUserId(sessionObj.getUserId());

                applicationRepo.insert(application);

                return json(gson.toJson(Map.of("message", "Application uploaded successfully")));
            } catch (UnauthorizedException e) {
                return jsonError(e.getMessage(), e.getStatus());
            }
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
