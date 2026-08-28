package com.horizontechga.jobapptracker.exceptions;

import lombok.Getter;

@Getter
public class InterviewNotFoundException extends RuntimeException{

    private final Long applicationId;
    private final Long interviewId;

    public InterviewNotFoundException(Long applicationId, Long interviewId) {
        super("Interview with id " + interviewId + " not found for job application " + applicationId);
        this.applicationId = applicationId;
        this.interviewId = interviewId;
    }


}
