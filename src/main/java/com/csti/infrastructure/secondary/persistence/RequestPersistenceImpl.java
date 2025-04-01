package com.csti.infrastructure.secondary.persistence;

import com.csti.core.domain.models.Request;
import com.csti.core.domain.models.RequestFilter;
import com.csti.core.ports.RequestPersistencePort;
import com.csti.infrastructure.secondary.persistence.mappers.RequestEntityMapper;
import com.csti.infrastructure.secondary.persistence.repository.ContactRepository;
import com.csti.infrastructure.secondary.persistence.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SuppressWarnings("all")
public class RequestPersistenceImpl implements RequestPersistencePort {
    private final RequestRepository requestRepository;
    private final ContactRepository contactRepository;
    private final RequestEntityMapper requestMapper;

    @Override
    public Mono<Request> saveRequest(Request request) {
        return requestRepository.save(requestMapper.toEntity(request))
                .flatMap(savedEntity ->
                        Flux.fromIterable(request.getContacts())
                                .map(contact -> requestMapper.toEntity(contact))
                                .doOnNext(contactEntity -> contactEntity.setRequestId(savedEntity.getId()))
                                .collectList()
                                .flatMap(contacts -> contactRepository.saveAll(contacts)
                                        .then(Mono.just(savedEntity)))
                )
                .flatMap(savedEntity ->
                        contactRepository.findAllByRequestId(savedEntity.getId())
                                .collectList()
                                .map(savedContacts -> {
                                    var domain = requestMapper.toDomain(savedEntity);
                                    domain.setContacts(savedContacts.stream()
                                            .map(requestMapper::toDomain)
                                            .collect(Collectors.toList()));
                                    return domain;
                                })
                );
    }

    @Override
    public Flux<Request> findAllRequests(RequestFilter requestFilter) {
        return requestRepository.findByParams(
                        requestFilter.getRequestType(),
                        requestFilter.getStartDate(),
                        requestFilter.getEndDate()
                )
                .flatMap(savedEntity ->
                        contactRepository.findAllByRequestId(savedEntity.getId())
                                .collectList()
                                .map(savedContacts -> {
                                    var domainRequest = requestMapper.toDomain(savedEntity);
                                    domainRequest.setContacts(savedContacts.stream()
                                            .map(requestMapper::toDomain)
                                            .collect(Collectors.toList()));
                                    return domainRequest;
                                })
                );
    }

    @Override
    public Mono<Request> findRequestById(String id) {
        return requestRepository.findById(id)
                .flatMap(requestEntity -> {
                    Request request = requestMapper.toDomain(requestEntity);
                    return contactRepository.findAllByRequestId(id)
                            .map(requestMapper::toDomain)
                            .collectList()
                            .map(contacts -> {
                                request.setContacts(contacts);
                                return request;
                            });
                });
    }

    @Override
    public Flux<Request.Contact> findContactsByRequestId(String requestId) {
        return contactRepository.findAllByRequestId(requestId)
                .map(requestMapper::toDomain);
    }
}
