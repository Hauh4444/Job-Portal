package com.jobportal.domain;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

public class User {
    public User() {}

    @BsonId
    private ObjectId id;
    private String username;
    private String hashedPassword;

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
