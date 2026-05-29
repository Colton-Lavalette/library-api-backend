package com.colton.library_api.exception;

import com.colton.library_api.dto.common.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now().toString()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoActiveLoanException.class)
    public ResponseEntity<ApiError> noActiveLoan(NoActiveLoanException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler({
            EmailAlreadyInUseException.class,
            DuplicateIsbnException.class,
            DuplicateCopyCodeException.class,
            BookAlreadyLoanedException.class,
            AuthorAlreadyLinkedException.class,
            GenreAlreadyLinkedException.class
    })
    public ResponseEntity<ApiError> conflict(RuntimeException ex, HttpServletRequest request) {
        return buildError(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler({
            MemberInactiveException.class,
            CopyHasLoanHistoryException.class
    })
    public ResponseEntity<ApiError> invalidRequest(RuntimeException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> typeMismatch(MethodArgumentTypeMismatchException ex,
                                                 HttpServletRequest request) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                "Invalid parameter: " + ex.getValue(),
                request
        );
    }

    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                status.value(),
                error,
                message,
                request.getRequestURI(),
                Instant.now().toString()
        );

        return ResponseEntity.status(status).body(apiError);
    }
}