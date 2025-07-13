package com.jobportal.repo;

import com.jobportal.domain.User;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

import org.bson.types.ObjectId;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.*;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final MongoCollection<User> users;

    public UserRepository() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        ConnectionString connString = new ConnectionString("mongodb://localhost:27017/jobportal");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .codecRegistry(codecRegistry)
                .build();
        MongoClient client = MongoClients.create(settings);
        MongoDatabase db = client.getDatabase("jobportal");
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
