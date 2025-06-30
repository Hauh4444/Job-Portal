package com.jobportal.domain;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Date;

public class Session {
    // MongoDB document ID, annotated as BSON ID for automatic mapping
    @BsonId
    private ObjectId id;

    // Id of the user
    private ObjectId userId;

    // Session token used for authentication
    private String sessionToken;

    // Datetime of the creation of the session
    private Date createdAt;

    // Datetime of the expiration of the session
    private Date expiresAt;

    // No-argument constructor required for POJO mapping by MongoDB driver
    public Session() {}

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
