package com.colton.library_api.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BookRequest(

        @NotBlank(message = "title is required")
        String title,

        @NotBlank(message = "isbn is required")
        @Pattern(
                regexp = "^[0-9]{13}$",
                message = "isbn must be a 13-digit number"
        )
        String isbn,

        Integer publishedYear
) {}