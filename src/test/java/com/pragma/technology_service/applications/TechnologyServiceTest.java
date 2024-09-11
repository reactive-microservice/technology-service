package com.pragma.technology_service.applications;

import com.pragma.technology_service.domain.Technology;
import com.pragma.technology_service.domain.gateways.TechnologyRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyServiceTest {

    @InjectMocks
    private TechnologyService technologyService;

    @Mock
    private TechnologyRepository technologyRepository;

    @Test
    void saveShouldBeSuccess() {
        Technology technology = new Technology(null, "Java", "Programming language");

        when(technologyRepository.findByName("Java")).thenReturn(Mono.empty());
        when(technologyRepository.save(technology)).thenReturn(Mono.just(technology));

        Mono<Technology> result = technologyService.save(technology);

        StepVerifier.create(result).expectNext(technology).verifyComplete();
    }

    @Test
    void saveShouldReturnErrorIfNameIsUsed(){
        Technology technology = new Technology(null, "Java", "Programming language");

        when(technologyRepository.findByName("Java")).thenReturn(Mono.just(technology));
        when(technologyRepository.save(technology)).thenReturn(Mono.just(technology));

        Mono<Technology> result = technologyService.save(technology);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException && throwable.getMessage().equals("Name already used"))
                .verify();
    }

    @Test
    void saveShouldReturnErrorIfNameIsEmpty(){
        Technology technology = new Technology(null, "", "Programing Language");

        Mono<Technology> result = Mono.just(technology)
                .filter(tech -> !tech.getName().isEmpty())// Emite el objeto si el campo es vacio
                .switchIfEmpty(Mono.error(new ConstraintViolationException("Name is required", null)));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ConstraintViolationException &&
                        throwable.getMessage().equals("Name is required"))
                .verify();
    }

    @Test
    void saveShouldReturnErrorIfDescriptionIsEmpty(){
        Technology technology = new Technology(null, "Java", "");

        Mono<Technology> result = Mono.just(technology)
                .filter(tech ->!tech.getDescription().isEmpty())// Emite el objeto si descripcion es vacia
                .switchIfEmpty(Mono.error(new ConstraintViolationException("Description is required", null)));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ConstraintViolationException &&
                        throwable.getMessage().equals("Description is required"))
                .verify();
    }

    @Test
    void saveShouldReturnErrorIfDescriptionIsLongerThan90Chars(){
        Technology technology = new Technology(null, "Java", "D".repeat(91));

        Mono<Technology> result = Mono.just(technology)
                .filter(tech -> tech.getDescription().length() <= 90)
                .switchIfEmpty(Mono.error(new ConstraintViolationException("Description must have a maximum length of 90 characters", null)));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ConstraintViolationException &&
                        throwable.getMessage().equals("Description must have a maximum length of 90 characters"))
                .verify();

    }

    @Test
    void saveShouldReturnErrorIfNameIsLongerThan50Chars(){
        Technology technology = new Technology(null, "J".repeat(51), "Programming Language");

        Mono<Technology> result = Mono.just(technology)
                .filter(tech -> tech.getName().length() <= 50)
                .switchIfEmpty(Mono.error(new ConstraintViolationException("Name must have a maximum length of 50 characters", null)));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ConstraintViolationException &&
                        throwable.getMessage().equals("Name must have a maximum length of 50 characters"))
                .verify();
    }

}