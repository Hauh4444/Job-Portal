package com.jobportal.repo;

import com.jobportal.domain.Profile;
import com.jobportal.config.MongoDBUtil;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.types.ObjectId;

public class ProfileRepository {
    private final MongoCollection<Profile> profiles;

    public ProfileRepository() {
        MongoDatabase db = MongoDBUtil.initializeMongoDB();
        profiles = db.getCollection("profiles", Profile.class);
    }

    public Profile findByUserId(ObjectId userId) {
        return profiles.find(Filters.eq("userId", userId)).first();
    }

    public void updateResume(ObjectId userId, String resume) {
        profiles.updateOne(Filters.eq("userId", userId), Updates.set("resume", resume));
    }

    public void updateProfile(ObjectId userId, Profile profile) {
        profiles.updateOne(Filters.eq("userId", userId), Updates.combine(
                Updates.set("name", profile.getName()),
                Updates.set("email", profile.getEmail()),
                Updates.set("phone", profile.getPhone()),
                Updates.set("country", profile.getCountry()),
                Updates.set("location", profile.getLocation())
        ));
    }
}
