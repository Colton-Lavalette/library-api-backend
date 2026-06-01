package com.colton.library_api.dto.book;

import jakarta.validation.constraints.NotNull;

public record AddAuthorRequest(

        @NotNull(message = "authorId is required")
        Long authorId,

        boolean primaryAuthor
) {}