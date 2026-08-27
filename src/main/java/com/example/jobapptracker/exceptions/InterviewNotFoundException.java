package com.example.jobapptracker.exceptions;

import lombok.Getter;

@Getter
public class InterviewNotFoundException extends RuntimeException{

    private final Long applicationId;
    private final Long interviewId;

    public InterviewNotFoundException(Long applicationId, Long interviewId) {
        super("For job application" + applicationId
                + "Interview not found with id: " + interviewId);
        this.applicationId = applicationId;
        this.interviewId = interviewId;
    }


}
