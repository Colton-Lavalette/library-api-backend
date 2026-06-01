package com.colton.library_api.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreRequest(
        @NotBlank(message = "name is required")
        String name
) {}
