package com.pragma.technology_service.infrastructure;

import com.pragma.technology_service.applications.TechnologyService;
import com.pragma.technology_service.domain.Technology;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/technologies")
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Technology> save(@RequestBody Technology technology) {
        return service.save(technology);
    }

}
