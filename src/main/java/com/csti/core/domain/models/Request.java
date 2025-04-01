package com.csti.core.domain.models;

import lombok.Data;

import java.util.List;

@Data
public class Request {
    private String id;
    private String brand;
    private String requestType;
    private String sentDate;
    private String contactNumber;
    private String contactName;
    private List<Contact> contacts;

    @Data
    public static class Contact {
        private String id;
        private String name;
        private String number;
    }
}
