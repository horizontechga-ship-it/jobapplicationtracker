package com.example.jobapptracker.application.dto;

import java.time.LocalDateTime;

public record CreateJobApplicationRequest (
        String company,
        String role,
        LocalDateTime appliedAt,
        String resumeName,
        String resumeVersion,
        String resumeUrl,
        String companyUrl
) { }