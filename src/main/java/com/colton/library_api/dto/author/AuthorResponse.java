package com.colton.library_api.dto.author;

public record AuthorResponse(
        Long id,
        String first,
        String middle,
        String last,
        Integer birthYear
) {}