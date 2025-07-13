package com.jobportal.api.routes;

import fi.iki.elonen.NanoHTTPD;

/**
 * Interface for handling HTTP routes.
 * Each implementing class should provide logic
 * to handle a specific set of HTTP requests.
 */
public interface RouteHandler {
    NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) throws Exception;
}
