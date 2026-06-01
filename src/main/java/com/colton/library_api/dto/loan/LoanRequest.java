package com.colton.library_api.dto.loan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoanRequest(

        @NotBlank(message = "memberCode is required")
        @Pattern(
                regexp = "^MEM-[A-Z0-9]{8}$",
                message = "memberCode must match format MEM-XXXXXXXX"
        )
        String memberCode,

        @NotBlank(message = "copyCode is required")
        @Pattern(
                regexp = "^BC-[A-Z0-9]{10}$",
                message = "copyCode must match format BC-XXXXXXXXXX"
        )
        String copyCode
) {}