package com.jobportal.repo;

import com.jobportal.domain.Profile;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

import org.bson.types.ObjectId;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.*;

import java.util.ArrayList;
import java.util.List;

public class ProfileRepository {
    private final MongoCollection<Profile> profiles;

    public ProfileRepository() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        ConnectionString connString = new ConnectionString("mongodb://localhost:27017/jobportal");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .codecRegistry(codecRegistry)
                .build();
        MongoClient client = MongoClients.create(settings);
        MongoDatabase db = client.getDatabase("jobportal");
        profiles = db.getCollection("profiles", Profile.class);
    }

    public Profile findByUserId(ObjectId userId) {
        return profiles.find(Filters.eq("userId", userId)).first();
    }

    public void updateResume(ObjectId userId, String resume) {
        profiles.updateOne(Filters.eq("userId", userId), Updates.set("resume", resume));
    }
}
