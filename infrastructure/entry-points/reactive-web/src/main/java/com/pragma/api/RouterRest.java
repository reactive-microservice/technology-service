package com.pragma.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/technology/{page}/{size}/{asc}"), handler::listenGETFindAllTechnologiesUseCase)
                .andRoute(POST("/api/technology"), handler::listenPOSTSaveTechnologyUseCase)
                .andRoute(GET("/api/technology/{id}"), handler::listenGETFindTechnologyByIdUseCase);
    }
}
