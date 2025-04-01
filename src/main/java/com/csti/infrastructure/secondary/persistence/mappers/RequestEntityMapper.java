package com.csti.infrastructure.secondary.persistence.mappers;

import com.csti.core.domain.models.Request;
import com.csti.infrastructure.secondary.persistence.entities.ContactEntity;
import com.csti.infrastructure.secondary.persistence.entities.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestEntityMapper {
    RequestEntityMapper INSTANCE = Mappers.getMapper(RequestEntityMapper.class);

    RequestEntity toEntity(Request request);

    Request toDomain(RequestEntity entity);

    ContactEntity toEntity(Request.Contact contact);

    Request.Contact toDomain(ContactEntity entity);
}
