package com.csti.infrastructure.secondary.persistence.repository;

import com.csti.infrastructure.secondary.persistence.entities.ContactEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ContactRepository extends ReactiveCrudRepository<ContactEntity, String> {
    Flux<ContactEntity> findAllByRequestId(String requestId);
}
