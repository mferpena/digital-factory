package com.csti.infrastructure.secondary.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("contacts")
public class ContactEntity {
    @Id
    private String id;

    @Column("request_id")
    private String requestId;

    @Column("name")
    private String name;

    @Column("number")
    private String number;
}
