package com.example.jobapptracker.application.dto;


import java.time.LocalDateTime;

public record InterviewResponse(
        Long id,
        LocalDateTime scheduledAt,
        String notes
) {
}