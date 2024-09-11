package com.pragma.technology_service.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Technology {
    private String id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must have a maximum length of 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 90, message = "Description must have a maximum length of 90 characters")
    private String description;
}

