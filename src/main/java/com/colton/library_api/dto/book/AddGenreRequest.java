package com.colton.library_api.dto.book;

public record AddGenreRequest(
        Long genreId,
        boolean primaryGenre
) {}