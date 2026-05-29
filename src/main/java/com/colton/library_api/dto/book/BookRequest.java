package com.colton.library_api.dto.book;

public record BookRequest(
    String title,
    String isbn,
    Integer publishedYear
) {}