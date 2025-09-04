package com.jobportal.domain;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.ArrayList;
import java.util.Date;

public class Job {
    public Job() {}

    @BsonId
    private ObjectId id;
    private String title;
    private String company;
    private String location;
    private String employmentType;
    private String workModel;
    private float salaryMin;
    private float salaryMax;
    private String descriptionShort;
    private String descriptionLong;
    private ArrayList<String> requirements;
    private ArrayList<String> responsibilities;
    private Date createdAt;
    private Date expiresAt;
    private String contactEmail;

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

    public String getWorkModel() {
        return workModel;
    }

    public void setWorkModel(String workModel) {
        this.workModel = workModel;
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

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
