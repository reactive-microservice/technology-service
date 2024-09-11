package com.pragma.technology_service.domain.gateways;

import com.pragma.technology_service.domain.Technology;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TechnologyRepository extends ReactiveMongoRepository<Technology,String> {
    Mono<Technology> findByName(String name);
}
