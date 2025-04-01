package com.scotiabank.infrastructure.config;

import com.scotiabank.infrastructure.primary.controllers.mappers.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Configuration
@SuppressWarnings("all")
public class AppConfig {
    @Bean
    public StudentMapper studentMapper() {
        return Mappers.getMapper(StudentMapper.class);
    }

    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Bean
    public MessageSource messageSource() {
        return new YamlMessageSource();
    }

    static class YamlMessageSource extends AbstractMessageSource {
        private final Map<String, Object> messages;

        public YamlMessageSource() {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("messages.yml")) {
                Yaml yaml = new Yaml();
                messages = yaml.load(inputStream);
                log.info("Loaded messages: {}", messages);
            } catch (Exception e) {
                throw new RuntimeException("Error loading messages.yml", e);
            }
        }

        @Override
        protected MessageFormat resolveCode(String code, Locale locale) {
            log.info("Resolviendo clave: {}", code);

            Object messageObj = messages;
            String[] keys = code.split("\\.");

            for (String key : keys) {
                if (messageObj instanceof Map) {
                    messageObj = ((Map<?, ?>) messageObj).get(key);
                } else {
                    log.error("No se encontró mensaje para la clave: {}", code);
                    return new MessageFormat("No se encontró el mensaje para la clave: " + code, locale);
                }
            }

            if (messageObj instanceof String) {
                return new MessageFormat((String) messageObj, locale);
            } else {
                log.error("No se encontró mensaje para la clave: {}", code);
                return new MessageFormat("No se encontró el mensaje para la clave: " + code, locale);
            }
        }
    }
}
