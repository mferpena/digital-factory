package com.csti.infrastructure.secondary.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("requests")
public class RequestEntity {
    @Id
    private String id;

    private String brand;

    @Column("request_type")
    private String requestType;

    @Column("sent_date")
    private LocalDate sentDate;

    @Column("contact_name")
    private String contactName;

    @Column("contact_number")
    private String contactNumber;
}
