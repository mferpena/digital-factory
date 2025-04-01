package com.scotiabank.infrastructure.secondary.persistence;

import com.scotiabank.AbstractTest;
import com.scotiabank.core.domain.enums.StudentStatus;
import com.scotiabank.core.domain.exceptions.CustomException;
import com.scotiabank.core.domain.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

class StudentPersistenceImplTest extends AbstractTest {
    private StudentPersistenceImpl studentPersistence;

    @BeforeEach
    void setUp() {
        studentPersistence = new StudentPersistenceImpl();
    }

    @Test
    void saveStudentSuccess() throws IOException {
        Student student = loadJsonFromFile("student.json", Student.class);
        Mono<Void> result = studentPersistence.save(student);
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void saveStudentAlreadyExistsShouldThrowException() throws IOException {
        Student student = loadJsonFromFile("student.json", Student.class);
        studentPersistence.save(student).block();
        Mono<Void> result = studentPersistence.save(student);
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof CustomException &&
                        throwable.getMessage().equals("Student with ID " + student.getId() + " already exists."))
                .verify();
    }

    @Test
    void findAllActiveShouldReturnEmptyListWhenNoActiveStudents() {
        Flux<Student> activeStudents = studentPersistence.findAllActive();
        StepVerifier.create(activeStudents)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void findAllActiveShouldReturnAllTenActiveStudents() {
        for (int i = 1; i <= 10; i++) {
            Student student = new Student();
            student.setId(String.valueOf(i));
            student.setFirstName("Student" + i);
            student.setLastName("Last" + i);
            student.setStatus(StudentStatus.ACTIVE);
            student.setAge(20 + i);

            studentPersistence.save(student).block();
        }
        Flux<Student> activeStudents = studentPersistence.findAllActive();
        StepVerifier.create(activeStudents)
                .expectNextCount(10)
                .verifyComplete();
    }
}