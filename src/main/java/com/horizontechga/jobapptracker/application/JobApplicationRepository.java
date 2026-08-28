package com.horizontechga.jobapptracker.application;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    @EntityGraph(attributePaths = "interviews")
    @Query("select j from JobApplication j")
    List<JobApplication> findAllWithInterviews();
}