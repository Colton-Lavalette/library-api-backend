package com.colton.library_api.dto.common;

public record ApiError(
        int status,
        String error,
        String message,
        String path,
        String timestamp
) {}