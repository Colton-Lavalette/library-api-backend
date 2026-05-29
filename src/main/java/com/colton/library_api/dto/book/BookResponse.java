package com.colton.library_api.dto.book;

public record BookResponse(
    Long id,
    String title,
    String isbn,
    Integer publishedYear
) {}
