package com.colton.library_api.dto.book;

public record AddAuthorRequest(
        Long authorId,
        boolean primaryAuthor
) {}