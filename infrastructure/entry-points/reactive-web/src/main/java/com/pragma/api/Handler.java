package com.pragma.api;

import com.pragma.model.technology.Technology;
import com.pragma.usecase.technologies.FindTechnologyByIdUseCase;
import com.pragma.usecase.technologies.SaveTechnologyUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.pragma.usecase.technologies.FindAllTechnologiesUseCase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final SaveTechnologyUseCase saveTechnologyUseCase;
    private final FindAllTechnologiesUseCase findAllTechnologiesUseCase;
    private final FindTechnologyByIdUseCase findTechnologyByIdUseCase;

    public Mono<ServerResponse> listenPOSTSaveTechnologyUseCase(ServerRequest request) {
        return request.bodyToMono(Technology.class)
                .flatMap(saveTechnologyUseCase::action)
                .flatMap(technology -> ServerResponse.ok().bodyValue(technology))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

    public Mono<ServerResponse> listenGETFindAllTechnologiesUseCase(ServerRequest request) {
        Integer page = Integer.valueOf(request.pathVariable("page"));
        Integer size = Integer.valueOf(request.pathVariable("size"));
        Boolean asc = Boolean.valueOf(request.pathVariable("asc"));

        return ServerResponse.ok().body(findAllTechnologiesUseCase.action(page, size, asc), Technology.class);
    }

    public Mono<ServerResponse> listenGETFindTechnologyByIdUseCase(ServerRequest request) {
        String id = request.pathVariable("id");
        return findTechnologyByIdUseCase.action(id)
                .flatMap(technology -> ServerResponse.ok().bodyValue(technology))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

}
