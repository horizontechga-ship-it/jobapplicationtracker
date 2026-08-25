package com.example.jobapptracker.application;


import com.example.jobapptracker.application.dto.CreateJobApplicationRequest;
import com.example.jobapptracker.application.dto.JobApplicationResponse;
import com.example.jobapptracker.application.dto.UpdateJobApplicationRequest;
import com.example.jobapptracker.application.services.JobApplicationService;
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
    public JobApplicationResponse create(@RequestBody CreateJobApplicationRequest request) {
        return service.create(request);
    };

    @PutMapping("/{id}")
    public JobApplicationResponse update(@PathVariable Long id,
                                         @RequestBody UpdateJobApplicationRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
