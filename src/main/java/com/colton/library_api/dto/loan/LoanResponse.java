package com.colton.library_api.dto.loan;

import java.time.LocalDate;

public record LoanResponse(
        Long id,
        String memberName,
        String bookTitle,
        String copyCode,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate
) {}
