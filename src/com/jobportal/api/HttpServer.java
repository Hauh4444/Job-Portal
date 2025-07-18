package com.jobportal.api;

import fi.iki.elonen.NanoHTTPD;
import com.jobportal.api.routes.RouteHandler;
import com.jobportal.api.routes.AuthRoutes;
import com.jobportal.api.routes.JobRoutes;
import com.jobportal.api.routes.ProfileRoutes;
import com.jobportal.api.routes.UserRoutes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpServer extends NanoHTTPD {
    private final List<RouteHandler> routeHandlers = new ArrayList<>();

    public HttpServer() throws IOException {
        super("0.0.0.0", 7000);

        routeHandlers.add(new AuthRoutes());
        routeHandlers.add(new JobRoutes());
        routeHandlers.add(new ProfileRoutes());
        routeHandlers.add(new UserRoutes());

        start(SOCKET_READ_TIMEOUT, false);

        System.out.println("Listening on http://0.0.0.0:7000");
    }

    /**
     * Override NanoHTTPD serve method to dispatch requests
     * to the appropriate route handler.
     *
     * @param session Incoming HTTP session containing request data
     * @return HTTP response generated by route handler or error if none matches
     */
    @Override
    public Response serve(IHTTPSession session) {
        try {
            for (RouteHandler handler : routeHandlers) {
                Response res = handler.handle(session);
                if (res != null) {
                    return res;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Server Error");
        }

        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not Found");
    }
}
