package com.colton.library_api.dto.book;

import com.colton.library_api.dto.author.AuthorResponse;

public record BookAuthorResponse(
        AuthorResponse author,
        boolean primaryAuthor
) {}