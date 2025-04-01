package com.scotiabank.core;

import com.scotiabank.core.domain.models.Student;
import com.scotiabank.core.ports.StudentPersistencePort;
import com.scotiabank.core.usecases.StudentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class StudentServiceImpl implements StudentUseCase {
    private final StudentPersistencePort todoPersistencePort;

    @Override
    public Mono<Void> saveStudent(Student student) {
        return todoPersistencePort.save(student);
    }

    @Override
    public Flux<Student> getActiveStudents() {
        return todoPersistencePort.findAllActive();
    }
}
