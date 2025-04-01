package com.csti.infrastructure.primary.controllers.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestFilterDto {
    private String requestType;
    private LocalDate startDate;
    private LocalDate endDate;
}
