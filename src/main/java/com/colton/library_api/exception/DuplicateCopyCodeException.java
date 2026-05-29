package com.colton.library_api.exception;

public class DuplicateCopyCodeException extends RuntimeException {
    public DuplicateCopyCodeException(String copyCode) {
        super("Copy already exists with code: " + copyCode);
    }
}
