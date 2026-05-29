package com.colton.library_api.exception;

public class CopyHasLoanHistoryException extends RuntimeException {
    public CopyHasLoanHistoryException(String message) {
        super(message);
    }
}
