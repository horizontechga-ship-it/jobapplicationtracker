package com.horizontechga.jobapptracker.application.dto;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateInterviewRequest(
        @NotNull
        LocalDateTime scheduledAt,
        String notes
) {
}