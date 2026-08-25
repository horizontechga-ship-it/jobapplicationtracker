package com.example.jobapptracker.application.services;

import com.example.jobapptracker.application.dto.CreateJobApplicationRequest;
import com.example.jobapptracker.application.dto.JobApplicationResponse;
import com.example.jobapptracker.application.JobApplication;
import com.example.jobapptracker.application.JobApplicationRepository;
import com.example.jobapptracker.application.JobApplicationMapper;
import com.example.jobapptracker.application.dto.UpdateJobApplicationRequest;
import com.example.jobapptracker.exceptions.JobApplicationNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


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

        var savedApplication = repository.save(application);

        return JobApplicationMapper.toResponse(savedApplication);
    }
}