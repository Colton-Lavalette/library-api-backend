package com.colton.library_api.dto.loan;

public record LoanRequest(
        String memberCode,
        String copyCode
) {}