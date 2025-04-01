package com.csti.core.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    private final List<ObjectError> errors;

    public ValidationException(List<ObjectError> errors) {
        super();
        this.errors = errors;
    }
}
