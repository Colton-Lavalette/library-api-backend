package com.colton.library_api.exception;

public class MemberInactiveException extends RuntimeException {
    public MemberInactiveException(String message) {
        super(message);
    }
}
