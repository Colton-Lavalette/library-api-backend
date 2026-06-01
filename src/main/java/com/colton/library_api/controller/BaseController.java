package com.colton.library_api.controller;

import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponse<T>> ok(
            String message,
            T data,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        HttpStatus.OK,
                        message,
                        data,
                        request.getRequestURI()
                )
        );
    }

    protected <T> ResponseEntity<ApiResponse<T>> created(
            T data,
            String path
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseFactory.success(
                        HttpStatus.CREATED,
                        "Resource created successfully",
                        data,
                        path
                )
        );
    }

    protected ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }
}