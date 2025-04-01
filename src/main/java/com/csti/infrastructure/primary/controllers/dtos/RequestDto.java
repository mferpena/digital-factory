package com.csti.infrastructure.primary.controllers.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequestDto {
    private String id;

    @NotBlank(message = "{brand.notBlank}")
    @Size(max = 50, message = "{brand.maxSize}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{brand.onlyLetters}")
    private String brand;

    @NotBlank(message = "{requestType.notBlank}")
    @Size(max = 30, message = "{requestType.maxSize}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{requestType.onlyLetters}")
    private String requestType;

    @NotNull(message = "{sentDate.notNull}")
    private LocalDate sentDate;

    @NotBlank(message = "{contactName.notBlank}")
    @Size(max = 22, message = "{contactName.maxSize}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{contactName.onlyLetters}")
    private String contactName;

    @NotBlank(message = "{contactNumber.notBlank}")
    @Pattern(regexp = "^\\d{9,15}$", message = "{contactNumber.invalidFormat}")
    private String contactNumber;

    private List<ContactDto> contacts;

    @Data
    public static class ContactDto {
        private String id;

        @NotBlank(message = "{contact.name.notBlank}")
        @Size(max = 22, message = "{contact.name.maxSize}")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{contact.name.onlyLetters}")
        private String name;

        @NotBlank(message = "{contact.number.notBlank}")
        @Pattern(regexp = "^\\d{9,15}$", message = "{contact.number.invalidFormat}")
        private String number;
    }
}
