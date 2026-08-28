package com.horizontechga.jobapptracker.application.dto;


import java.time.LocalDateTime;
import java.util.List;

public record JobApplicationResponse(
        Long id,
        String company,
        String role,
        LocalDateTime appliedAt,
        String resumeName,
        String resumeVersion,
        String resumeUrl,
        String companyUrl,
        List<InterviewResponse> interviews
) {
}