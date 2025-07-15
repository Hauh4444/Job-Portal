package com.jobportal.api.exceptions;

import fi.iki.elonen.NanoHTTPD;

public class UnauthorizedException extends RuntimeException {
    private final NanoHTTPD.Response.Status status;

    public UnauthorizedException(String message, NanoHTTPD.Response.Status status) {
        super(message);
        this.status = status;
    }

    public NanoHTTPD.Response.Status getStatus() {
        return status;
    }
}