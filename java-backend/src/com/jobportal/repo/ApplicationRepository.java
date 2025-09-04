package com.jobportal.repo;

import com.jobportal.domain.Application;
import com.jobportal.config.MongoDBUtil;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.types.ObjectId;

public class ApplicationRepository {
    private final MongoCollection<Application> applications;

    public ApplicationRepository() {
        MongoDatabase db = MongoDBUtil.initializeMongoDB();
        applications = db.getCollection("applications", Application.class);
    }

    public boolean findByJobIdAndUserId(ObjectId jobId, ObjectId userId) {
        return applications.find(Filters.and(
                Filters.eq("jobId", jobId),
                Filters.eq("userId", userId)
        )).first() != null;
    }

    public void insert(Application application) {
        applications.insertOne(application);
    }
}
