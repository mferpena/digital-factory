package com.csti.infrastructure.primary.controllers.routers;

import com.csti.infrastructure.primary.controllers.handlers.RequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@SuppressWarnings("all")
public class RequestRouter {
    @Bean
    public RouterFunction<ServerResponse> route(RequestHandler handler) {
        return RouterFunctions.route(POST("/api/requests"), handler::createRequest)
                .andRoute(POST("/api/requests/"), handler::listRequests)
                .andRoute(GET("/api/requests/{id}"), handler::getRequestById)
                .andRoute(POST("/api/requests/export/csv"), handler::exportRequestsAsCSV);
    }
}
