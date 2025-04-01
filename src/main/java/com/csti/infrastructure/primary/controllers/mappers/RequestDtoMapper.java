package com.csti.infrastructure.primary.controllers.mappers;

import com.csti.core.domain.models.Request;
import com.csti.core.domain.models.RequestFilter;
import com.csti.infrastructure.primary.controllers.dtos.RequestDto;
import com.csti.infrastructure.primary.controllers.dtos.RequestFilterDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RequestDtoMapper {
    RequestDtoMapper INSTANCE = Mappers.getMapper(RequestDtoMapper.class);

    Request toDomain(RequestDto dto);

    RequestDto toDTO(Request domain);

    Request.Contact toDomain(RequestDto.ContactDto dto);

    RequestDto.ContactDto toDTO(Request.Contact domain);

    List<Request.Contact> toDomainList(List<RequestDto.ContactDto> dtoList);

    List<RequestDto.ContactDto> toDTOList(List<Request.Contact> domainList);

    RequestFilter toDomain(RequestFilterDto filterDto);

    RequestFilterDto toDTO(RequestFilter domain);
}
