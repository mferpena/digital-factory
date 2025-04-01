package com.csti.infrastructure.primary.controllers.handlers;

import com.csti.core.domain.exceptions.ValidationException;
import com.csti.core.domain.helpers.ResponseHelper;
import com.csti.core.usecases.RequestUseCase;
import com.csti.infrastructure.primary.controllers.dtos.RequestDto;
import com.csti.infrastructure.primary.controllers.dtos.RequestFilterDto;
import com.csti.infrastructure.primary.controllers.mappers.RequestDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@SuppressWarnings("all")
public class RequestHandler {
    private final RequestUseCase useCase;
    private final RequestDtoMapper mapper;
    private final ResponseHelper responseHelper;

    public Mono<ServerResponse> createRequest(ServerRequest request) {
        return request.bodyToMono(RequestDto.class)
                .flatMap(dto -> responseHelper.validateRequest(dto))
                .map(mapper::toDomain)
                .flatMap(useCase::createRequest)
                .map(mapper::toDTO)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .onErrorResume(BindException.class, e -> Mono.error(new ValidationException(e.getAllErrors())));
    }

    public Mono<ServerResponse> listRequests(ServerRequest request) {
        return request.bodyToMono(RequestFilterDto.class)
                .map(mapper::toDomain)
                .flatMapMany(useCase::listRequests)
                .map(mapper::toDTO)
                .collectList()
                .flatMap(dtoList -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dtoList))
                .onErrorResume(BindException.class, e -> Mono.error(new ValidationException(e.getAllErrors())));
    }

    public Mono<ServerResponse> getRequestById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.getRequestById(id)
                .flatMap(detail -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(detail));
    }

    public Mono<ServerResponse> exportRequestsAsCSV(ServerRequest request) {
        return request.bodyToMono(RequestFilterDto.class)
                .map(mapper::toDomain)
                .flatMap(useCase::exportRequestsAsCSV)
                .flatMap(csv -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .header("Content-Disposition", "attachment; filename=\"requests.csv\"")
                        .bodyValue(csv))
                .onErrorResume(BindException.class, e -> Mono.error(new ValidationException(e.getAllErrors())));
    }
}
