package com.csti.core;

import com.csti.core.domain.models.Request;
import com.csti.core.domain.models.RequestFilter;
import com.csti.core.ports.RequestPersistencePort;
import com.csti.core.usecases.RequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class RequestUseCaseImpl implements RequestUseCase {
    private final RequestPersistencePort persistencePort;

    @Override
    public Mono<Request> createRequest(Request request) {
        return persistencePort.saveRequest(request);
    }

    @Override
    public Flux<Request> listRequests(RequestFilter requestFilter) {
        return persistencePort.findAllRequests(requestFilter);
    }

    @Override
    public Mono<Request> getRequestById(String id) {
        return persistencePort.findRequestById(id);
    }

    @Override
    public Mono<String> exportRequestsAsCSV(RequestFilter requestFilter) {
        return persistencePort.findAllRequests(requestFilter)
                .collectList()
                .map(requests -> {
                    StringBuilder csvContent = new StringBuilder();

                    csvContent.append("Request ID;Brand;Request Type;Contact Name;Contact Number;Contact ID;Contact Name;Contact Number\n");

                    requests.forEach(request -> {
                        String baseRequestRow = String.join(";",
                                request.getId(),
                                request.getBrand(),
                                request.getRequestType(),
                                request.getContactName(),
                                request.getContactNumber()
                        );

                        request.getContacts().forEach(contact -> {
                            String contactRow = String.join(";",
                                    baseRequestRow,
                                    contact.getId(),
                                    contact.getName(),
                                    contact.getNumber()
                            );
                            csvContent.append(contactRow).append("\n");
                        });

                        if (request.getContacts().isEmpty()) {
                            csvContent.append(baseRequestRow).append("\n");
                        }
                    });

                    return csvContent.toString();
                });
    }
}
