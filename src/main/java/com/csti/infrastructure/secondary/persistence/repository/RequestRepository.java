package com.csti.infrastructure.secondary.persistence.repository;

import com.csti.infrastructure.secondary.persistence.entities.RequestEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface RequestRepository extends ReactiveCrudRepository<RequestEntity, String> {
    @Query("SELECT * FROM requests " +
            "WHERE (:requestType IS NULL OR :requestType = '' OR request_type = :requestType) " +
            "AND (:startDate IS NULL OR sent_date >= :startDate) " +
            "AND (:endDate IS NULL OR sent_date <= :endDate)")
    Flux<RequestEntity> findByParams(
            @Param("requestType") String requestType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
