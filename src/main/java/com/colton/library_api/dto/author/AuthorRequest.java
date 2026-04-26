package com.colton.library_api.dto.author;

public record AuthorRequest(
        String first,
        String middle,
        String last,
        Integer birthYear
) {}