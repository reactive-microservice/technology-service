package com.pragma.usecase.technologies;

import com.pragma.model.technology.Technology;
import com.pragma.model.technology.gateways.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindTechnologyByIdUseCase {

    private final TechnologyRepository technologyRepository;

    public Mono<Technology> action(String id) {
        return technologyRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Technology not found")));
    }

}
