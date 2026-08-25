package com.example.jobapptracker.application;

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