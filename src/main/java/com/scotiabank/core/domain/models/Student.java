package com.scotiabank.core.domain.models;

import com.scotiabank.core.domain.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private StudentStatus status;
    private Integer age;
}
