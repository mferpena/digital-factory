package com.scotiabank.core.ports;

import com.scotiabank.core.domain.models.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentPersistencePort {
    Mono<Void> save(Student student);

    Flux<Student> findAllActive();
}
