package com.jobportal.domain;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.ArrayList;
import java.util.Date;

public class Job {
    // MongoDB document ID, annotated as BSON ID for automatic mapping
    @BsonId
    private ObjectId id;

    // Title of the job posting
    private String title;

    // Company of the job listing
    private String company;

    // Location of the job
    private String location;

    // Type of employment (e.g. Full-time, Part-time, Contract)
    private String employmentType;

    // Minimum salary offered
    private float salaryMin;

    // Maximum salary offered
    private float salaryMax;

    // Description or details about the job
    private String description;

    // List of job requirements
    private ArrayList<String> requirements;

    // List of job responsibilities
    private ArrayList<String> responsibilities;

    // Date when the job was created/posted
    private Date createdAt;

    // Date when the job posting expires
    private Date expiresAt;

    // Flag indicating if the job is remote
    private boolean isRemote;

    // Contact email for applications
    private String contactEmail;

    // No-argument constructor required for POJO mapping by MongoDB driver
    public Job() {}

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public float getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(float salaryMin) {
        this.salaryMin = salaryMin;
    }

    public float getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(float salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }

    public ArrayList<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(ArrayList<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
