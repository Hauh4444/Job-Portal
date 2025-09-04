package com.jobportal.repo;

import com.jobportal.domain.Job;
import com.jobportal.config.MongoDBUtil;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class JobRepository {
    private final MongoCollection<Job> jobs;

    public JobRepository() {
        MongoDatabase db = MongoDBUtil.initializeMongoDB();
        jobs = db.getCollection("jobs", Job.class);
    }

    public List<Job> findAll() {
        return jobs.find().into(new ArrayList<>());
    }

    public void insert(Job job) {
        jobs.insertOne(job);
    }

    public void delete(String id) {
        jobs.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
}
