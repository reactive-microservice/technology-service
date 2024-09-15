package com.pragma.usecase.technologies;

import com.pragma.model.technology.Technology;
import com.pragma.model.technology.gateways.TechnologyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;


@ExtendWith(MockitoExtension.class)
class SaveTechnologyUseCaseTest {
    @Mock
    private TechnologyRepository repository;

    @InjectMocks
    private SaveTechnologyUseCase saveTechnologyUseCase;

    @Test
    void shouldSaveTechnologyWhenNameIsNotUsed() {
        Technology technology = new Technology("1", "Java", "Java is a programming language");

        given(repository.findByName(technology.getName())).willReturn(Mono.empty());
        given(repository.save(any(Technology.class))).willReturn(Mono.just(technology));

        StepVerifier.create(saveTechnologyUseCase.action(technology))
                .expectNext(technology)
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenNameIsAlreadyUsed() {
        Technology technology = new Technology("1", "Java", "Java is a programming language");

        given(repository.findByName(technology.getName())).willReturn(Mono.just(technology));
        given(repository.save(any(Technology.class))).willReturn(Mono.just(technology));

        StepVerifier.create(saveTechnologyUseCase.action(technology))
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Name already used"))
                .verify();
    }
}