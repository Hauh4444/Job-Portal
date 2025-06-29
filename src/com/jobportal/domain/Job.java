package com.jobportal.domain;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

public class Job {
    // MongoDB document ID, annotated as BSON ID for automatic mapping
    @BsonId
    private ObjectId id;

    // Title of the job posting
    private String title;

    // Description or details about the job
    private String description;

    // No-argument constructor required for POJO mapping by MongoDB driver
    public Job() {}

    // Getter for the MongoDB ObjectId
    public ObjectId getId() { return id; }

    // Setter for the MongoDB ObjectId
    public void setId(ObjectId id) { this.id = id; }

    // Getter for the job title
    public String getTitle() { return title; }

    // Setter for the job title
    public void setTitle(String title) { this.title = title; }

    // Getter for the job description
    public String getDescription() { return description; }

    // Setter for the job description
    public void setDescription(String description) { this.description = description; }
}
