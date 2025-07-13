package com.jobportal.repo;

import com.jobportal.domain.Job;

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

public class JobRepository {
    private final MongoCollection<Job> jobs;

    public JobRepository() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        ConnectionString connString = new ConnectionString("mongodb://localhost:27017/jobportal");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .codecRegistry(codecRegistry)
                .build();
        MongoClient client = MongoClients.create(settings);
        MongoDatabase db = client.getDatabase("jobportal");
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
