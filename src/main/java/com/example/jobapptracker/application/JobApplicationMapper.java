package com.example.jobapptracker.application;

import com.example.jobapptracker.application.dto.InterviewResponse;
import com.example.jobapptracker.application.dto.JobApplicationResponse;

public final class JobApplicationMapper {

    private JobApplicationMapper() {
    }

    public static JobApplicationResponse toResponse(JobApplication application) {
        var interviews = application.getInterviews()
                .stream()
                .map(interview -> new InterviewResponse(
                        interview.getId(),
                        interview.getScheduledAt(),
                        interview.getNotes()
                ))
                .toList();

        return new JobApplicationResponse(
                application.getId(),
                application.getCompany(),
                application.getRole(),
                application.getAppliedAt(),
                application.getResumeName(),
                application.getResumeUrl(),
                application.getCompanyUrl(),
                interviews
        );
    }
}