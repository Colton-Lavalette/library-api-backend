package com.colton.library_api.dto.member;

import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        @NotBlank(message = "first is required")
        String first,

        String middle,

        @NotBlank(message = "last is required")
        String last,

        @NotBlank(message = "email is required")
        String email
) {}
