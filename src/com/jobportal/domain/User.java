package com.jobportal.domain;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

public class User {
    // MongoDB document ID, annotated as BSON ID for automatic mapping
    @BsonId
    private ObjectId id;

    // Username of the user
    private String username;

    // Hashed password of the user
    private String hashedPassword;

    // No-argument constructor required for POJO mapping by MongoDB driver
    public User() {}

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
