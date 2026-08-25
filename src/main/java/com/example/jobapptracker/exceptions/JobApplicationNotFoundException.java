package com.example.jobapptracker.exceptions;


public class JobApplicationNotFoundException extends RuntimeException {

    private final Long applicationId;

    public JobApplicationNotFoundException(Long applicationId) {
        super("Job application not found with id: " + applicationId);
        this.applicationId = applicationId;
    }

    public Long getApplicationId() {
        return applicationId;
    }
}