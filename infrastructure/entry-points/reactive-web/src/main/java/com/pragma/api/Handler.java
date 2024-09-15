package com.pragma.api;

import com.pragma.model.technology.Technology;
import com.pragma.usecase.technologies.SaveTechnologyUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.pragma.usecase.technologies.FindAllTechnologiesUseCase;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final SaveTechnologyUseCase saveTechnologyUseCase;
    private final FindAllTechnologiesUseCase findAllTechnologiesUseCase;

    public Mono<ServerResponse> listenPOSTSaveTechnologyUseCase(ServerRequest request) {
        return request.bodyToMono(Technology.class)
                .flatMap(saveTechnologyUseCase::action)
                .flatMap(technology -> ServerResponse.ok().bodyValue(technology))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

    public Mono<ServerResponse> listenGETFindAllTechnologiesUseCase(ServerRequest request) {
        Integer page = Integer.valueOf(request.queryParam("page").orElse("0"));
        Integer size = Integer.valueOf(request.queryParam("size").orElse("10"));
        Boolean asc = Boolean.valueOf(request.queryParam("asc").orElse("true"));

        return ServerResponse.ok().body(findAllTechnologiesUseCase.action(page, size, asc), Technology.class);
    }

}
