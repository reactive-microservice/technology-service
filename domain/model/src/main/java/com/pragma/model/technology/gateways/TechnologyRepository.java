package com.pragma.model.technology.gateways;

import com.pragma.model.technology.Technology;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TechnologyRepository extends ReactiveMongoRepository<Technology,String> {
    Mono<Technology> findByName(String name);
}
