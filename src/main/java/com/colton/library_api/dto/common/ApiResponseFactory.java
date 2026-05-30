package com.colton.library_api.dto.common;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ApiResponseFactory {

    public static <T> ApiResponse<T> success(
            HttpStatus status,
            String message,
            T data,
            String path
    ) {
        return new ApiResponse<>(
                status.value(),
                message,
                data,
                path,
                Instant.now().toString()
        );
    }
}