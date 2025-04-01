package com.csti.core.domain.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestFilter {
    private String requestType;
    private LocalDate startDate;
    private LocalDate endDate;
}
