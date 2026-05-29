package com.colton.library_api.exception;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String isbn) {
        super("ISBN already exists: " + isbn);
    }
}
