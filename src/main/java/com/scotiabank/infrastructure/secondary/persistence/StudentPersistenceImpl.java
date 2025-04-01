package com.scotiabank.infrastructure.secondary.persistence;

import com.scotiabank.core.domain.enums.StudentStatus;
import com.scotiabank.core.domain.exceptions.CustomException;
import com.scotiabank.core.domain.models.Student;
import com.scotiabank.core.ports.StudentPersistencePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@SuppressWarnings("unused")
public class StudentPersistenceImpl implements StudentPersistencePort {
    private final Map<String, Student> studentsDB = new ConcurrentHashMap<>();

    public Mono<Void> save(Student student) {
        if (studentsDB.containsKey(student.getId())) {
            return Mono.error(new CustomException("Student with ID " + student.getId() + " already exists."));
        }
        studentsDB.put(student.getId(), student);
        return Mono.empty();
    }

    public Flux<Student> findAllActive() {
        return Flux.fromIterable(studentsDB.values())
                .filter(student -> student.getStatus() == StudentStatus.ACTIVE);
    }
}
