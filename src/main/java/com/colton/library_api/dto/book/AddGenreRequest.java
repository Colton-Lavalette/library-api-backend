package com.colton.library_api.dto.book;

import jakarta.validation.constraints.NotNull;

public record AddGenreRequest(

        @NotNull(message = "genreId is required")
        Long genreId,

        boolean primaryGenre
) {}