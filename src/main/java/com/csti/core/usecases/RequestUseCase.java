package com.csti.core.usecases;

import com.csti.core.domain.models.Request;
import com.csti.core.domain.models.RequestFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestUseCase {
    Mono<Request> createRequest(Request request);

    Flux<Request> listRequests(RequestFilter requestFilter);

    Mono<Request> getRequestById(String id);

    Mono<String> exportRequestsAsCSV(RequestFilter requestFilter);
}

