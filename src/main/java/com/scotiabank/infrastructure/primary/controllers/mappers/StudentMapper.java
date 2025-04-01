package com.scotiabank.infrastructure.primary.controllers.mappers;

import com.scotiabank.core.domain.models.Student;
import com.scotiabank.infrastructure.primary.controllers.dtos.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "age", source = "age")
    Student toEntity(StudentDto studentDto);
}
