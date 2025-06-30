package com.jobportal.repo;

import com.jobportal.domain.User;
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

public class UserRepository {
    // MongoCollection representing the "users" collection with documents mapped to User class
    private final MongoCollection<User> users;

    public UserRepository() {
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
        // Get the "users" collection from the database, mapping documents to User objects
        users = db.getCollection("users", User.class);
    }

    public List<User> findAll() {
        return users.find().into(new ArrayList<>());
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

    public void insert(User user) {
        users.insertOne(user);
    }

    public void delete(String id) {
        users.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
}
