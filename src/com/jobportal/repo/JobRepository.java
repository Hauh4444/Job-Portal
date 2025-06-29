package com.jobportal.repo;

import com.jobportal.domain.Job;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;

import org.bson.types.ObjectId;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.*;

import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

public class JobRepository {
    // MongoCollection representing the "jobs" collection with documents mapped to Job class
    private final MongoCollection<Job> jobs;

    public JobRepository() {
        // Create a CodecRegistry with automatic POJO mapping enabled
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        // Combine the default CodecRegistry with the POJO CodecRegistry
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        // Configure MongoClientSettings to use the combined CodecRegistry
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .build();

        // Create a new MongoClient instance with the configured settings
        MongoClient client = MongoClients.create(settings);
        // Connect to the "jobportal" database
        MongoDatabase db = client.getDatabase("jobportal");
        // Get the "jobs" collection from the database, mapping documents to Job objects
        jobs = db.getCollection("jobs", Job.class);
    }

    /**
     * Retrieve all Job documents from the collection.
     *
     * @return List of all Job objects found
     */
    public List<Job> findAll() {
        // Find all documents and collect them into a List
        return jobs.find().into(new ArrayList<>());
    }

    /**
     * Insert a new Job document into the collection.
     *
     * @param job the Job object to insert
     */
    public void insert(Job job) {
        jobs.insertOne(job);
    }

    /**
     * Delete a Job document by its MongoDB _id.
     *
     * @param id the String representation of the ObjectId to delete
     */
    public void delete(String id) {
        // Convert string id to ObjectId and delete matching document
        jobs.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
}
