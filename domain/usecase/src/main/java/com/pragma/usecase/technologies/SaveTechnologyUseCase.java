package com.pragma.usecase.technologies;

import com.pragma.model.technology.Technology;
import com.pragma.model.technology.gateways.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SaveTechnologyUseCase {

    private final TechnologyRepository repository;

    public Mono<Technology> action(Technology technology) {
        return repository.findByName(technology.getName())
                .flatMap(existingTech -> Mono.error(new IllegalArgumentException("Name already used")))
                .switchIfEmpty(repository.save(technology))
                .cast(Technology.class);
    }
}
