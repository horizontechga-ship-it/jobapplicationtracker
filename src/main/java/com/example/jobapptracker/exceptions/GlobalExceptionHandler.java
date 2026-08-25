package com.example.jobapptracker.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobApplicationNotFoundException.class)
    public ProblemDetail handleJobApplicationNotFound(
            JobApplicationNotFoundException exception) {

        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problem.setTitle("Job application not found");
        problem.setProperty(
                "applicationId",
                exception.getApplicationId()
        );

        return problem;
    }
}