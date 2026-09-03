package com.horizontechga.jobapptracker.application.services;

import com.horizontechga.jobapptracker.application.Interview;
import com.horizontechga.jobapptracker.application.dto.*;
import com.horizontechga.jobapptracker.application.JobApplication;
import com.horizontechga.jobapptracker.application.JobApplicationRepository;
import com.horizontechga.jobapptracker.application.JobApplicationMapper;

import com.horizontechga.jobapptracker.exceptions.InterviewNotFoundException;
import com.horizontechga.jobapptracker.exceptions.JobApplicationNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public JobApplicationResponse create(CreateJobApplicationRequest request) {
        var application = new JobApplication(
                request.company(),
                request.role(),
                request.appliedAt(),
                request.resumeName(),
                request.resumeVersion(),
                request.resumeUrl(),
                request.companyUrl()
        );

        var savedApplication = repository.save(application);

        return JobApplicationMapper.toResponse(savedApplication);
    }

    public JobApplicationResponse getById(Long id) {
        var application = repository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException(id));

        return JobApplicationMapper.toResponse(application);
    }

    public List<JobApplicationResponse> getAll() {
        return repository.findAllWithInterviews()
                .stream()
                .map(JobApplicationMapper::toResponse)
                .toList();
    }

    @Transactional
    public JobApplicationResponse update(Long id, UpdateJobApplicationRequest request) {

        var application = repository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException(id));

        application.update(
                request.company(),
                request.role(),
                request.appliedAt(),
                request.resumeName(),
                request.resumeVersion(),
                request.resumeUrl(),
                request.companyUrl()
        );

        return JobApplicationMapper.toResponse(application);
    }

    @Transactional
    public void delete(Long id) {
        var application = repository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException(id));
        repository.delete(application);
    }

    @Transactional
    public JobApplicationResponse addInterview(
            Long applicationId,
            CreateInterviewRequest request) {

        var application = repository.findById(applicationId)
                .orElseThrow(() ->
                        new JobApplicationNotFoundException(applicationId));

        var interview = new Interview(
                request.scheduledAt(),
                request.notes(),
                application
        );

        application.addInterview(interview);

        //The generated id happens after insert, so we flush before returning
        repository.flush();

        return JobApplicationMapper.toResponse(application);
    }

    @Transactional
    public JobApplicationResponse updateInterview(
            Long applicationId,
            Long interviewId,
            UpdateInterviewRequest request) {

        var application = repository.findById(applicationId)
                .orElseThrow(() ->
                        new JobApplicationNotFoundException(applicationId));

        var interview = application.getInterviews()
                .stream()
                .filter(existingInterview ->
                        Objects.equals(
                                existingInterview.getId(),
                                interviewId
                        ))
                .findFirst()
                .orElseThrow(() ->
                        new InterviewNotFoundException(
                                applicationId,
                                interviewId
                        ));

        interview.update(
                request.scheduledAt(),
                request.notes()
        );

        return JobApplicationMapper.toResponse(application);
    }

    @Transactional
    public void deleteInterview(
            Long applicationId,
            Long interviewId) {

        var application = repository.findById(applicationId)
                .orElseThrow(() ->
                        new JobApplicationNotFoundException(applicationId));

        var interview = application.getInterviews()
                .stream()
                .filter(existingInterview ->
                        Objects.equals(
                                existingInterview.getId(),
                                interviewId
                        ))
                .findFirst()
                .orElseThrow(() ->
                        new InterviewNotFoundException(
                                applicationId,
                                interviewId
                        ));

        application.removeInterview(interview);
    }

    public InterviewResponse getInterview(
            Long applicationId,
            Long interviewId) {

        var application = repository.findById(applicationId)
                .orElseThrow(() ->
                        new JobApplicationNotFoundException(applicationId));

        var interview = application.getInterviews()
                .stream()
                .filter(existingInterview ->
                        Objects.equals(existingInterview.getId(), interviewId))
                .findFirst()
                .orElseThrow(() ->
                        new InterviewNotFoundException(
                                applicationId,
                                interviewId
                        ));

        return JobApplicationMapper.toResponse(interview);
    }


}