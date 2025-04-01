package com.scotiabank.infrastructure.primary.controllers.routers;

import com.scotiabank.infrastructure.primary.controllers.handlers.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@SuppressWarnings("unused")
public class StudentRouter {
    @Bean
    public RouterFunction<ServerResponse> studentRoutes(StudentHandler handler) {
        return RouterFunctions
                .nest(path("/students"),
                        route(GET(""), handler::getActiveStudents)
                                .and(route(POST(""), handler::createStudent))
                );
    }
}
