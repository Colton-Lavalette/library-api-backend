package com.colton.library_api.dto.bookcopy;

public record BookCopyResponse(
        Long id,
        String copyCode,
        Long bookId,
        boolean inCirculation
) {}