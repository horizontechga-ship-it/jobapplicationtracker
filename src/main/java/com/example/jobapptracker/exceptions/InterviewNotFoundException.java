package com.example.jobapptracker.exceptions;

import lombok.Getter;

@Getter
public class InterviewNotFoundException extends RuntimeException{

    private final Long applicationId;
    private final Long interviewId;

    public InterviewNotFoundException(Long applicationId, Long interviewId) {
        String message = "For job application" + applicationId
                + "Interview not found with id: " + interviewId;
        super(message);
        this.applicationId = applicationId;
        this.interviewId = interviewId;
    }


}
