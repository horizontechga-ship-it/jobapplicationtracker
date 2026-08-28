package com.horizontechga.jobapptracker.application;


import com.horizontechga.jobapptracker.application.dto.*;
import com.horizontechga.jobapptracker.application.services.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {


    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService  service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public JobApplicationResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<JobApplicationResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationResponse create(@Valid @RequestBody CreateJobApplicationRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public JobApplicationResponse update(@PathVariable Long id,
                                         @Valid @RequestBody UpdateJobApplicationRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        service.delete(id);
    }

    @PostMapping("/{applicationId}/interviews")
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationResponse addInterview(
            @PathVariable Long applicationId,
            @Valid @RequestBody CreateInterviewRequest request) {

        return service.addInterview(applicationId, request);
    }

    @PutMapping("/{applicationId}/interviews/{interviewId}")
    public JobApplicationResponse updateInterview(
            @PathVariable Long applicationId,
            @PathVariable Long interviewId,
            @Valid @RequestBody UpdateInterviewRequest request) {

        return service.updateInterview(
                applicationId,
                interviewId,
                request
        );
    }

    @DeleteMapping("/{applicationId}/interviews/{interviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInterview(
            @PathVariable Long applicationId,
            @PathVariable Long interviewId) {

        service.deleteInterview(
                applicationId,
                interviewId
        );
    }

    @GetMapping("/{applicationId}/interviews/{interviewId}")
    public InterviewResponse getInterview(
            @PathVariable Long applicationId,
            @PathVariable Long interviewId) {

        return service.getInterview(
                applicationId,
                interviewId
        );
    }
}
