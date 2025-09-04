package com.jobportal.repo;

import com.jobportal.domain.Session;
import com.jobportal.config.MongoDBUtil;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.types.ObjectId;

public class SessionRepository {
    private final MongoCollection<Session> sessions;

    public SessionRepository() {
        MongoDatabase db = MongoDBUtil.initializeMongoDB();
        sessions = db.getCollection("sessions", Session.class);
    }

    public Session findBySessionToken(String sessionToken) {
        return sessions.find(Filters.eq("sessionToken", sessionToken)).first();
    }

    public void insert(Session session) {
        sessions.insertOne(session);
    }

    public void delete(String id) {
        sessions.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
}
