package com.pragma.usecase.technologies;

import com.pragma.model.technology.Technology;
import com.pragma.model.technology.gateways.TechnologyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FindAllTechnologiesUseCaseTest {

    @InjectMocks
    private FindAllTechnologiesUseCase findAllTechnologiesUseCase;

    @Mock
    private TechnologyRepository technologyRepository;

    private Sort defaultSort;

    @BeforeEach
    void setUp() {
        defaultSort = Sort.by(Sort.Direction.ASC, "name");
    }


    @Test
    void findAllTechnologiesShouldReturnTwoItems() {

        Technology java = new Technology("1","Java", "Language programming");
        Technology python = new Technology("2","Python", "Language programming");
        given(technologyRepository.findAll(defaultSort)).willReturn(Flux.just(java, python));

        Flux<Technology> result = findAllTechnologiesUseCase.action(0, 2, true);

        StepVerifier.create(result)
                .expectNext(java)
                .expectNext(python)
                .verifyComplete();

    }

    @Test
    void findAllTechnologiesShouldReturnEmptyWhenNoData() {

        given(technologyRepository.findAll(defaultSort)).willReturn(Flux.empty());

        Flux<Technology> result = findAllTechnologiesUseCase.action(0, 2, true);

        StepVerifier.create(result)
                .verifyComplete();
    }

}