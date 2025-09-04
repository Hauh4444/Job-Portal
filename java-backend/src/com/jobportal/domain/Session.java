package com.jobportal.domain;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Date;

public class Session {
    public Session() {}

    @BsonId
    private ObjectId id;
    private ObjectId userId;
    private String sessionToken;
    private Date createdAt;
    private Date expiresAt;

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
