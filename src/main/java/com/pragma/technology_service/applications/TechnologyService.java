package com.pragma.technology_service.applications;

import com.pragma.technology_service.domain.Technology;
import com.pragma.technology_service.domain.gateways.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository repository;

    public Mono<Technology> save(Technology technology){
        return this.repository
                .findByName(technology.getName())
                .flatMap(exist -> Mono.error(new IllegalArgumentException("Name already used")))
                .switchIfEmpty(this.repository.save(technology))
                .cast(Technology.class);
    }

}
