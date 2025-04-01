package com.scotiabank.core.usecases;

import com.scotiabank.core.domain.models.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentUseCase {
    Mono<Void> saveStudent(Student student);

    Flux<Student> getActiveStudents();
}
