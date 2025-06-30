package com.jobportal.repo;

import com.jobportal.domain.Session;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

import org.bson.types.ObjectId;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.*;

import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

public class SessionRepository {
    // MongoCollection representing the "sessions" collection with documents mapped to Session class
    private final MongoCollection<Session> sessions;

    public SessionRepository() {
        // Create a CodecRegistry with automatic POJO mapping enabled
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        // Combine the default CodecRegistry with the POJO CodecRegistry
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        // Define connection string
        ConnectionString connString = new ConnectionString("mongodb://localhost:27017/jobportal");
        // Configure MongoClientSettings to use the combined CodecRegistry
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .codecRegistry(codecRegistry)
                .build();
        // Create a new MongoClient instance with the configured settings
        MongoClient client = MongoClients.create(settings);
        // Connect to the "jobportal" database
        MongoDatabase db = client.getDatabase("jobportal");
        // Get the "sessions" collection from the database, mapping documents to Session objects
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
