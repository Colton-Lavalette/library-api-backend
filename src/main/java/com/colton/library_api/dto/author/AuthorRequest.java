package com.colton.library_api.dto.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequest(

        @NotBlank(message = "first is required")
        String first,

        String middle,

        @NotBlank(message = "last is required")
        String last,

        Integer birthYear
) {}