package com.horizontechga.jobapptracker.application;

import jakarta.persistence.*;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String role;

    private LocalDateTime appliedAt;

    private String resumeName;

    private String resumeVersion;

    private String resumeUrl;

    private String companyUrl;

    @OneToMany(
            mappedBy = "jobApplication",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Interview> interviews = new ArrayList<>();

    public JobApplication(
            String company,
            String role,
            LocalDateTime appliedAt,
            String resumeName,
            String resumeVersion,
            String resumeUrl,
            String companyUrl) {

        this.company = company;
        this.role = role;
        this.appliedAt = appliedAt;
        this.resumeName = resumeName;
        this.resumeVersion = resumeVersion;
        this.resumeUrl = resumeUrl;
        this.companyUrl = companyUrl;
    }

    public void update(
            String company,
            String role,
            LocalDateTime appliedAt,
            String resumeName,
            String resumeVersion,
            String resumeUrl,
            String companyUrl) {

        this.company = company;
        this.role = role;
        this.appliedAt = appliedAt;
        this.resumeName = resumeName;
        this.resumeVersion = resumeVersion;
        this.resumeUrl = resumeUrl;
        this.companyUrl = companyUrl;
    }

    public void addInterview(Interview interview) {
        interviews.add(interview);
    }

    public void removeInterview(Interview interview) {
        interviews.remove(interview);
    }
}