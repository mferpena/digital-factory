package com.scotiabank;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AbstractTest {
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    protected <T> T loadJsonFromFile(String filename, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new ClassPathResource(filename).getFile(), clazz);
    }

    protected <T> List<T> loadJsonListFromFile(String fileName, Class<T> clazz) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException("File not found: " + fileName);
        }
        return objectMapper.readValue(inputStream, new TypeReference<List<T>>() {});
    }
}
