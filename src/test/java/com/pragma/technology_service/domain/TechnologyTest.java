package com.pragma.technology_service.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TechnologyTest {

    private Validator validator;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void technologyShouldReturnErrorIfNameIsLongerThan50Chars() {
        Technology tech = new Technology("1", "A".repeat(51), "Valid description");

        Set<ConstraintViolation<Technology>> violations = validator.validate(tech);

        assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("name") &&
                        v.getMessage().equals("Name must have a maximum length of 50 characters")
        ));
    }


    @Test
    void technologyShouldReturnErrorIfNameIsBlank() {
        Technology tech = new Technology("1", "", "Valid description");

        Set<ConstraintViolation<Technology>> violations = validator.validate(tech);

        assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("name") &&
                        v.getMessage().equals("Name is required")
        ));
    }

    @Test
    void technologyShouldReturnErrorIfDescriptionIsLongerThan90Chars() {
        Technology tech = new Technology("1", "Valid name", "A".repeat(91));

        Set<ConstraintViolation<Technology>> violations = validator.validate(tech);

        assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("description") &&
                        v.getMessage().equals("Description must have a maximum length of 90 characters")
        ));
    }

    @Test
    void technologyShouldReturnErrorIfDescriptionIsBlank() {
        Technology tech = new Technology("1", "Valid name", "");

        Set<ConstraintViolation<Technology>> violations = validator.validate(tech);

        assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("description") &&
                        v.getMessage().equals("Description is required")
        ));
    }

}