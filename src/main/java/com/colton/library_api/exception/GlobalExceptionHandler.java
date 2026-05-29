package com.colton.library_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> notFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoActiveLoanException.class)
    public ResponseEntity<String> noActiveLoan(NoActiveLoanException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({
            EmailAlreadyInUseException.class,
            DuplicateIsbnException.class,
            DuplicateCopyCodeException.class,
            BookAlreadyLoanedException.class,
            AuthorAlreadyLinkedException.class,
            GenreAlreadyLinkedException.class
    })
    public ResponseEntity<String> conflict(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler({
            MemberInactiveException.class,
            CopyHasLoanHistoryException.class
    })
    public ResponseEntity<String> invalidRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}