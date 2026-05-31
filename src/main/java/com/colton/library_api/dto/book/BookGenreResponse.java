package com.colton.library_api.dto.book;

import com.colton.library_api.dto.genre.GenreResponse;

public record BookGenreResponse(
        GenreResponse genre,
        boolean primaryGenre
) {}
