package com.csti.core.ports;

import com.csti.core.domain.models.Request;
import com.csti.core.domain.models.RequestFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestPersistencePort {
    Mono<Request> saveRequest(Request request);

    Flux<Request> findAllRequests(RequestFilter requestFilter);

    Mono<Request> findRequestById(String id);

    Flux<Request.Contact> findContactsByRequestId(String requestId);
}
