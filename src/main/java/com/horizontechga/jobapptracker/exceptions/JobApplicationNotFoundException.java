package com.horizontechga.jobapptracker.exceptions;

import lombok.Getter;

@Getter
public class JobApplicationNotFoundException extends RuntimeException {

    private final Long applicationId;

    public JobApplicationNotFoundException(Long applicationId) {
        super("Job application not found with id: " + applicationId);
        this.applicationId = applicationId;
    }


}