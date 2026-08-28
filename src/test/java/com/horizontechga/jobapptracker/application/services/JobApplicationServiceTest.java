package com.horizontechga.jobapptracker.application.services;

import com.horizontechga.jobapptracker.application.JobApplication;
import com.horizontechga.jobapptracker.application.JobApplicationRepository;
import com.horizontechga.jobapptracker.exceptions.JobApplicationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository repository;

    @InjectMocks
    private JobApplicationService service;

    @Test
    void getByIdReturnsApplicationWhenFound() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 27, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        var response = service.getById(1L);

        assertEquals("Acme Corp", response.company());
        assertEquals("Java Developer", response.role());
    }

    @Test
    void getByIdThrowsWhenApplicationNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(
                JobApplicationNotFoundException.class,
                () -> service.getById(1L)
        );
    }
}