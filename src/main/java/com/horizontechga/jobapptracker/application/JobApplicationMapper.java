package com.horizontechga.jobapptracker.application;

import com.horizontechga.jobapptracker.application.dto.InterviewResponse;
import com.horizontechga.jobapptracker.application.dto.JobApplicationResponse;

public final class JobApplicationMapper {

    private JobApplicationMapper() {
    }

    public static JobApplicationResponse toResponse(JobApplication application) {

        var interviews = application.getInterviews()
                .stream()
                .map(JobApplicationMapper::toResponse)
                .toList();

        return new JobApplicationResponse(
                application.getId(),
                application.getCompany(),
                application.getRole(),
                application.getAppliedAt(),
                application.getResumeName(),
                application.getResumeVersion(),
                application.getResumeUrl(),
                application.getCompanyUrl(),
                interviews
        );
    }

    public static InterviewResponse toResponse(Interview interview) {
        return new InterviewResponse(
                interview.getId(),
                interview.getScheduledAt(),
                interview.getNotes()
        );
    }
}