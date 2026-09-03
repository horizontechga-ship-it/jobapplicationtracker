package com.horizontechga.jobapptracker.application.services;

import com.horizontechga.jobapptracker.application.Interview;
import com.horizontechga.jobapptracker.application.JobApplication;
import com.horizontechga.jobapptracker.application.JobApplicationRepository;
import com.horizontechga.jobapptracker.application.dto.CreateInterviewRequest;
import com.horizontechga.jobapptracker.application.dto.CreateJobApplicationRequest;
import com.horizontechga.jobapptracker.application.dto.UpdateInterviewRequest;
import com.horizontechga.jobapptracker.application.dto.UpdateJobApplicationRequest;
import com.horizontechga.jobapptracker.exceptions.InterviewNotFoundException;
import com.horizontechga.jobapptracker.exceptions.JobApplicationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void createSavesApplicationAndReturnsResponse() {
        var request = new CreateJobApplicationRequest(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        when(repository.save(any(JobApplication.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var response = service.create(request);

        var applicationCaptor = ArgumentCaptor.forClass(JobApplication.class);

        verify(repository).save(applicationCaptor.capture());

        var savedApplication = applicationCaptor.getValue();

        assertEquals("Acme Corp", savedApplication.getCompany());
        assertEquals("Java Developer", savedApplication.getRole());

        assertEquals("Acme Corp", response.company());
        assertEquals("Java Developer", response.role());
    }

    @Test
    void deleteRemovesApplicationWhenFound() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        service.delete(1L);

        verify(repository).delete(application);
    }

    @Test
    void deleteThrowsWhenApplicationNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                JobApplicationNotFoundException.class,
                () -> service.delete(1L)
        );

        verify(repository, never()).delete(any(JobApplication.class));
    }

    @Test
    void updateChangesApplicationAndReturnsResponse() {
        var application = new JobApplication(
                "Old Corp",
                "Junior Developer",
                LocalDateTime.of(2026, 8, 1, 12, 0),
                "Old Resume",
                "v1",
                "https://example.com/old-resume",
                "https://old.example.com"
        );

        var request = new UpdateJobApplicationRequest(
                "Acme Corp",
                "Senior Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v2",
                "https://example.com/resume",
                "https://example.com"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        var response = service.update(1L, request);

        assertEquals("Acme Corp", application.getCompany());
        assertEquals("Senior Java Developer", application.getRole());

        assertEquals("Acme Corp", response.company());
        assertEquals("Senior Java Developer", response.role());
    }

    @Test
    void updateThrowsWhenApplicationNotFound() {
        var request = new UpdateJobApplicationRequest(
                "Acme Corp",
                "Senior Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v2",
                "https://example.com/resume",
                "https://example.com"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                JobApplicationNotFoundException.class,
                () -> service.update(1L, request)
        );
    }

    @Test
    void addInterviewAddsInterviewAndReturnsUpdatedApplication() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        var request = new CreateInterviewRequest(
                LocalDateTime.of(2026, 9, 5, 14, 30),
                "Technical interview"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        var response = service.addInterview(1L, request);

        assertEquals(1, application.getInterviews().size());
        assertEquals(
                LocalDateTime.of(2026, 9, 5, 14, 30),
                application.getInterviews().getFirst().getScheduledAt()
        );
        assertEquals(
                "Technical interview",
                application.getInterviews().getFirst().getNotes()
        );

        assertEquals(1, response.interviews().size());

        verify(repository).flush();
    }

    @Test
    void addInterviewThrowsWhenApplicationNotFound() {
        var request = new CreateInterviewRequest(
                LocalDateTime.of(2026, 9, 5, 14, 30),
                "Technical interview"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                JobApplicationNotFoundException.class,
                () -> service.addInterview(1L, request)
        );

        verify(repository, never()).flush();
    }

    @Test
    void getInterviewReturnsInterviewWhenFound() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        var interview = new Interview(
                LocalDateTime.of(2026, 9, 5, 14, 30),
                "Technical interview",
                application
        );

        ReflectionTestUtils.setField(interview, "id", 42L);

        application.addInterview(interview);

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        var response = service.getInterview(1L, 42L);

        assertEquals(
                LocalDateTime.of(2026, 9, 5, 14, 30),
                response.scheduledAt()
        );
        assertEquals("Technical interview", response.notes());
        assertEquals(42L, response.id());
    }

    @Test
    void getAllReturnsMappedApplications() {
        var firstApplication = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        var secondApplication = new JobApplication(
                "Globex",
                "Backend Engineer",
                LocalDateTime.of(2026, 9, 1, 9, 30),
                "Backend Resume",
                "v2",
                "https://example.com/backend-resume",
                "https://globex.example.com"
        );

        when(repository.findAllWithInterviews())
                .thenReturn(List.of(firstApplication, secondApplication));

        var responses = service.getAll();

        assertEquals(2, responses.size());

        assertEquals("Acme Corp", responses.get(0).company());
        assertEquals("Java Developer", responses.get(0).role());

        assertEquals("Globex", responses.get(1).company());
        assertEquals("Backend Engineer", responses.get(1).role());
    }

    @Test
    void getInterviewThrowsWhenInterviewNotFound() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        assertThrows(
                InterviewNotFoundException.class,
                () -> service.getInterview(1L, 42L)
        );
    }

    @Test
    void getInterviewThrowsWhenApplicationNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                JobApplicationNotFoundException.class,
                () -> service.getInterview(1L, 42L)
        );
    }

    @Test
    void updateInterviewChangesInterviewAndReturnsUpdatedApplication() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        var interview = new Interview(
                LocalDateTime.of(2026, 9, 5, 14, 30),
                "Initial interview",
                application
        );

        ReflectionTestUtils.setField(interview, "id", 42L);

        application.addInterview(interview);

        var request = new UpdateInterviewRequest(
                LocalDateTime.of(2026, 9, 6, 10, 0),
                "Updated interview notes"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        var response = service.updateInterview(
                1L,
                42L,
                request
        );

        assertEquals(
                LocalDateTime.of(2026, 9, 6, 10, 0),
                interview.getScheduledAt()
        );
        assertEquals(
                "Updated interview notes",
                interview.getNotes()
        );

        assertEquals(1, response.interviews().size());
        assertEquals(
                LocalDateTime.of(2026, 9, 6, 10, 0),
                response.interviews().getFirst().scheduledAt()
        );
        assertEquals(
                "Updated interview notes",
                response.interviews().getFirst().notes()
        );
    }

    @Test
    void updateInterviewThrowsWhenInterviewNotFound() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        var request = new UpdateInterviewRequest(
                LocalDateTime.of(2026, 9, 6, 10, 0),
                "Updated interview notes"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        assertThrows(
                InterviewNotFoundException.class,
                () -> service.updateInterview(
                        1L,
                        42L,
                        request
                )
        );
    }

    @Test
    void updateInterviewThrowsWhenApplicationNotFound() {
        var request = new UpdateInterviewRequest(
                LocalDateTime.of(2026, 9, 6, 10, 0),
                "Updated interview notes"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                JobApplicationNotFoundException.class,
                () -> service.updateInterview(
                        1L,
                        42L,
                        request
                )
        );
    }

    @Test
    void deleteInterviewRemovesInterviewWhenFound() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        var interview = new Interview(
                LocalDateTime.of(2026, 9, 5, 14, 30),
                "Technical interview",
                application
        );

        ReflectionTestUtils.setField(interview, "id", 42L);

        application.addInterview(interview);

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        service.deleteInterview(1L, 42L);

        assertEquals(0, application.getInterviews().size());
    }

    @Test
    void deleteInterviewThrowsWhenInterviewNotFound() {
        var application = new JobApplication(
                "Acme Corp",
                "Java Developer",
                LocalDateTime.of(2026, 8, 31, 12, 0),
                "Java Resume",
                "v1",
                "https://example.com/resume",
                "https://example.com"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(application));

        assertThrows(
                InterviewNotFoundException.class,
                () -> service.deleteInterview(1L, 42L)
        );
    }

    @Test
    void deleteInterviewThrowsWhenApplicationNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                JobApplicationNotFoundException.class,
                () -> service.deleteInterview(1L, 42L)
        );
    }

}