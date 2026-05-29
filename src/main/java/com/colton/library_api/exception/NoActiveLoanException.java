package com.colton.library_api.exception;

public class NoActiveLoanException extends RuntimeException {
    public NoActiveLoanException(String message) {
        super(message);
    }
}
