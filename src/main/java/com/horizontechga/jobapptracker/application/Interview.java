package com.horizontechga.jobapptracker.application;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime scheduledAt;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id", nullable = false)
    private JobApplication jobApplication;

    public Interview(LocalDateTime scheduledAt,
            String notes,
            JobApplication jobApplication) {

        this.scheduledAt = scheduledAt;
        this.notes = notes;
        this.jobApplication = jobApplication;
    }

    public void update(LocalDateTime scheduledAt, String notes) {

        this.scheduledAt = scheduledAt;
        this.notes = notes;
    }
}
