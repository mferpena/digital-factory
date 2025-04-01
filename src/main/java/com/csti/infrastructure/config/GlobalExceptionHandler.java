package com.csti.infrastructure.config;

import com.csti.core.domain.exceptions.CustomException;
import com.csti.core.domain.exceptions.ValidationException;
import com.csti.core.domain.models.ErrorDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.csti.core.domain.helpers.ResponseHelper.*;

@Component
@Order(-2)
@RequiredArgsConstructor
@SuppressWarnings("all")
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ErrorDetails errorDetails;

        if (ex instanceof ValidationException) {
            ValidationException validationException = (ValidationException) ex;
            errorDetails = ErrorDetails.builder()
                    .status(getStatusFromCustomException(ex))
                    .message("Validation failed")
                    .errors(extractErrorMessages(validationException.getErrors()))
                    .build();
        } else if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            errorDetails = ErrorDetails.builder()
                    .status(getStatusFromCustomException(ex))
                    .message(customException.getMessage())
                    .build();
        } else {
            errorDetails = ErrorDetails.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage())
                    .build();
        }

        exchange.getResponse().setStatusCode(errorDetails.getStatus());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return writeResponse(exchange, errorDetails, errorDetails.getStatus());
    }
}
