package com.colton.library_api.dto.common;

public record ApiResponse<T>(
        int status,
        String message,
        T data,
        String path,
        String timestamp
) {}