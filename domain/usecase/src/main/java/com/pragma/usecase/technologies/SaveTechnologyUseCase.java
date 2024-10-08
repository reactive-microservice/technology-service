package com.pragma.usecase.technologies;

import com.pragma.model.technology.Technology;
import com.pragma.model.technology.gateways.TechnologyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Validated
public class SaveTechnologyUseCase {

    private final TechnologyRepository repository;

    public Mono<Technology> action(@Valid Technology technology) {
        return repository.findByName(technology.getName())
                .flatMap(existingTech -> Mono.error(new IllegalArgumentException("Name already used"))
                            .cast(Technology.class))
                .switchIfEmpty(repository.save(technology));
    }
}
