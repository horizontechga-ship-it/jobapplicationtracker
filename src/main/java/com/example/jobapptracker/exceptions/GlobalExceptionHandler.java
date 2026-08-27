package com.example.jobapptracker.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(InterviewNotFoundException.class)
    public ProblemDetail handleInterviewNotFound(
            InterviewNotFoundException exception) {

        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problem.setTitle("Job application not found");
        problem.setProperty(
                "applicationId",
                exception.getApplicationId()
        );
        problem.setProperty(
                "interviewId",
                exception.getInterviewId()
        );

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationFailure(
            MethodArgumentNotValidException exception) {

        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Request validation failed"
        );

        problem.setTitle("Invalid Request");

        var errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        problem.setProperty("errors", errors);

        return problem;
    }

    private record ValidationError(
            String field,
            String message
    ){}

}