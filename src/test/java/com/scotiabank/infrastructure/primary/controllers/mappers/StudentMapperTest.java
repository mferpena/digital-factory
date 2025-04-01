package com.scotiabank.infrastructure.primary.controllers.mappers;

import com.scotiabank.AbstractTest;
import com.scotiabank.core.domain.enums.StudentStatus;
import com.scotiabank.core.domain.models.Student;
import com.scotiabank.infrastructure.primary.controllers.dtos.StudentDto;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest extends AbstractTest {
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    @Test
    void toEntity() throws IOException {
        StudentDto studentDto = loadJsonFromFile("student.json", StudentDto.class);
        Student student = studentMapper.toEntity(studentDto);

        assertNotNull(student);
        assertEquals(studentDto.getId(), student.getId());
        assertEquals(studentDto.getFirstName(), student.getFirstName());
        assertEquals(studentDto.getLastName(), student.getLastName());
        assertEquals(StudentStatus.valueOf(studentDto.getStatus()), student.getStatus());
        assertEquals(studentDto.getAge(), student.getAge());
    }
}