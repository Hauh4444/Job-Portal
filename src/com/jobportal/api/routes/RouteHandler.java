package com.jobportal.api.routes;

import fi.iki.elonen.NanoHTTPD;

/**
 * Interface for handling HTTP routes.
 * Each implementing class should provide logic
 * to handle a specific set of HTTP requests.
 */
public interface RouteHandler {
    /**
     * Handle an incoming HTTP session/request.
     *
     * @param session the incoming HTTP session containing request data
     * @return a NanoHTTPD.Response if this handler processes the request, or null otherwise
     * @throws Exception if an error occurs during request handling
     */
    NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) throws Exception;
}
