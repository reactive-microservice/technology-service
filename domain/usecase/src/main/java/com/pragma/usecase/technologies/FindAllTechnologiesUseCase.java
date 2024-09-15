package com.pragma.usecase.technologies;

import com.pragma.model.technology.Technology;
import com.pragma.model.technology.gateways.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FindAllTechnologiesUseCase {

    private final TechnologyRepository repository;

    public Flux<Technology> action(Integer page, Integer size, Boolean asc){
        return this.repository.findAll(Sort.by(asc.equals(true) ? Sort.Order.asc("name") : Sort.Order.desc("name"))) // sort by name asc or desc
                .skip((long) page * size) // skip the first page * size elements
                .take(size); // take the next size elements
    }
}