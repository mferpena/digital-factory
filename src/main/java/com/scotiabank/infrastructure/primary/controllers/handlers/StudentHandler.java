package com.scotiabank.infrastructure.primary.controllers.handlers;

import com.scotiabank.core.domain.exceptions.ValidationException;
import com.scotiabank.core.domain.helpers.ResponseHelper;
import com.scotiabank.core.domain.models.Student;
import com.scotiabank.core.usecases.StudentUseCase;
import com.scotiabank.infrastructure.primary.controllers.dtos.StudentDto;
import com.scotiabank.infrastructure.primary.controllers.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@SuppressWarnings("all")
@RequiredArgsConstructor
public class StudentHandler {
    private final StudentUseCase studentUseCase;
    private final StudentMapper studentMapper;
    private final ResponseHelper responseHelper;

    public Mono<ServerResponse> createStudent(ServerRequest request) {
        return request.bodyToMono(StudentDto.class)
                .flatMap(dto -> responseHelper.validateRequest(dto))
                .map(studentMapper::toEntity)
                .flatMap(studentUseCase::saveStudent)
                .flatMap(savedStudent -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedStudent))
                .onErrorResume(BindException.class, e -> Mono.error(new ValidationException(e.getAllErrors())));
    }

    public Mono<ServerResponse> getActiveStudents(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentUseCase.getActiveStudents(), Student.class);
    }
}
