package com.csti.core.domain.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@SuppressWarnings("all")
public class ResponseHelper {
    private final Validator validator;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Mono<Void> writeResponse(ServerWebExchange exchange, Object responseObject, HttpStatus status) {
        try {
            String responseBody = objectMapper.writeValueAsString(responseObject);

            exchange.getResponse().setStatusCode(status);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(Mono.just(
                    exchange.getResponse().bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8))
            ));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    public static List<String> extractErrorMessages(List<ObjectError> errors) {
        List<String> errorMessages = new ArrayList<>();

        for (ObjectError error : errors) {
            if (error instanceof FieldError fieldError) {
                errorMessages.add(fieldError.getDefaultMessage());
            }
        }

        return errorMessages;
    }

    public static HttpStatus getStatusFromCustomException(Object ce) {
        ResponseStatus responseStatus = ce.getClass().getAnnotation(ResponseStatus.class);
        if (responseStatus != null) {
            return responseStatus.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public <T> Mono<T> validateRequest(T dto) {
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(dto, dto.getClass().getSimpleName());
        validator.validate(dto, errors);

        if (errors.hasErrors()) {
            return Mono.error(new BindException(errors));
        }

        return Mono.just(dto);
    }
}
