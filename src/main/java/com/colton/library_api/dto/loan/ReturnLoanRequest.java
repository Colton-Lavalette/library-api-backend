package com.colton.library_api.dto.loan;

import jakarta.validation.constraints.NotBlank;

public record ReturnLoanRequest(

        @NotBlank(message = "copyCode is required")
        String copyCode
) {}
