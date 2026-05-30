package com.colton.library_api.controller;

import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.dto.loan.LoanRequest;
import com.colton.library_api.dto.loan.LoanResponse;
import com.colton.library_api.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanResponse>> getLoan(@PathVariable Long id) {

        LoanResponse loan = loanService.findById(id);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        HttpStatus.OK,
                        "Loan retrieved successfully",
                        loan,
                        "/loans/" + id
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LoanResponse>> createLoan(
            @Valid @RequestBody LoanRequest request
    ) {
        LoanResponse loan = loanService.createLoan(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseFactory.success(
                        HttpStatus.CREATED,
                        "Loan created successfully",
                        loan,
                        "/loans/" + loan.id()
                ));
    }

}
