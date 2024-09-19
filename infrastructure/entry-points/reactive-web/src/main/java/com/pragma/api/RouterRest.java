package com.pragma.api;

import com.pragma.model.technology.Technology;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class RouterRest {

    @RouterOperations({
        @RouterOperation(
                path = "/api/technology/{page}/{size}/{asc}",
                beanClass = Handler.class,
                beanMethod = "listenGETFindAllTechnologiesUseCase",
                operation = @Operation(
                        operationId = "FindAllTechnologies",
                        summary = "Find All Technologies",
                        parameters = {
                                @Parameter(name = "page", description = "Page number", required = true, in = ParameterIn.PATH),
                                @Parameter(name = "size", description = "Page size", required = true, in = ParameterIn.PATH),
                                @Parameter(name = "asc", description = "Sort ascending", required = true, in = ParameterIn.PATH)
                        }
                )
        ),
        @RouterOperation(
                path = "/api/technology",
                beanClass = Handler.class,
                beanMethod = "listenPOSTSaveTechnologyUseCase",
                operation = @Operation(
                        operationId = "saveTechnology",
                        summary = "Save a new technology",
                        requestBody = @RequestBody(
                                description = "Technology to be saved",
                                required = true,
                                content = @Content(
                                        schema = @Schema(implementation = Technology.class)
                                )
                        )
                )
        ),
        @RouterOperation(
                path = "/api/technology/{id}",
                beanClass = Handler.class,
                beanMethod = "listenGETFindTechnologyByIdUseCase",
                operation = @Operation(
                        operationId = "findTechnologyById",
                        summary = "Find technology by ID",
                        parameters = {
                                @Parameter(name = "id", description = "Technology ID", required = true, in = ParameterIn.PATH)
                        }
                )
        )
})
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/technology/{page}/{size}/{asc}"), handler::listenGETFindAllTechnologiesUseCase)
                .andRoute(POST("/api/technology"), handler::listenPOSTSaveTechnologyUseCase)
                .andRoute(GET("/api/technology/{id}"), handler::listenGETFindTechnologyByIdUseCase);
    }
}
