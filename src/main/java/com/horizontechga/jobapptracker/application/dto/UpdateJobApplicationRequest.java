package com.horizontechga.jobapptracker.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateJobApplicationRequest(
        @NotBlank
        String company,
        @NotBlank
        String role,
        @NotNull
        LocalDateTime appliedAt,
        @NotBlank
        String resumeName,
        @Size(max = 100)
        String resumeVersion,
        String resumeUrl,
        String companyUrl
) {
}