package com.csti.core.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
