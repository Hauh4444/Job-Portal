package com.jobportal.api.services;

import com.jobportal.domain.Session;
import com.jobportal.repo.SessionRepository;
import com.jobportal.api.exceptions.UnauthorizedException;

import fi.iki.elonen.NanoHTTPD;

import java.util.Date;

public class AuthServices {
    private static final SessionRepository sessionRepo = new SessionRepository();

    public static Session validateSession(NanoHTTPD.IHTTPSession session) {
        String authHeader = session.getHeaders().get("authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing or invalid Authorization header", NanoHTTPD.Response.Status.UNAUTHORIZED);
        }

        String sessionToken = authHeader.substring("Bearer ".length());
        Session sessionObj = sessionRepo.findBySessionToken(sessionToken);
        if (sessionObj == null) {
            throw new UnauthorizedException("Invalid session token", NanoHTTPD.Response.Status.UNAUTHORIZED);
        }

        Date now = new Date();
        if (sessionObj.getExpiresAt().before(now)) {
            throw new UnauthorizedException("Session expired", NanoHTTPD.Response.Status.UNAUTHORIZED);
        }

        return sessionObj;
    }
}