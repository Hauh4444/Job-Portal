package com.jobportal.repo;

import com.jobportal.domain.User;
import com.jobportal.config.MongoDBUtil;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.types.ObjectId;

public class UserRepository {
    private final MongoCollection<User> users;

    public UserRepository() {
        MongoDatabase db = MongoDBUtil.initializeMongoDB();
        users = db.getCollection("users", User.class);
    }

    public User findById(ObjectId id) {
        return users.find(Filters.eq("_id", id)).first();
    }

    public User findByUsername(String username) {
        return users.find(Filters.eq("username", username)).first();
    }

    public User findByUsernameAndPassword(String username, String hashedPassword) {
        return users.find(Filters.and(
                Filters.eq("username", username),
                Filters.eq("hashedPassword", hashedPassword)
        )).first();
    }
}
