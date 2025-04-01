package com.scotiabank.infrastructure.primary.controllers.dtos;

import com.scotiabank.core.domain.validator.ValidStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentDto {
    @NotNull(message = "{student.id.notnull}")
    private String id;

    @NotNull(message = "{student.firstname.notnull}")
    private String firstName;

    @NotNull(message = "{student.lastname.notnull}")
    private String lastName;

    @NotNull(message = "{student.status.notnull}")
    @ValidStatus(message = "{student.status.invalid}")
    private String status;

    @Min(value = 0, message = "{student.age.min}")
    private Integer age;
}
