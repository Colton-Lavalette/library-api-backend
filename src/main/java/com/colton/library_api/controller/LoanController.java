package com.colton.library_api.controller;

import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.dto.loan.LoanRequest;
import com.colton.library_api.dto.loan.LoanResponse;
import com.colton.library_api.dto.loan.ReturnLoanRequest;
import com.colton.library_api.model.LoanStatus;
import com.colton.library_api.service.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController extends BaseController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LoanResponse>>> getAllLoans(
            @RequestParam(required = false) LoanStatus status,
            HttpServletRequest httpServletRequest
    ) {

        List<LoanResponse> loans = loanService.findLoans(status);

        return ok(
                "Loans retrieved successfully",
                loans,
                httpServletRequest
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanResponse>> getLoan(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {

        LoanResponse loan = loanService.findById(id);

        return ok(
                "Loan retrieved successfully",
                loan,
                httpServletRequest
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LoanResponse>> createLoan(
            @Valid @RequestBody LoanRequest loanRequest
    ) {
        LoanResponse loan = loanService.createLoan(loanRequest);

        return created(
                loan,
                "/loans/" + loan.id()
        );
    }

    @PostMapping("/return")
    public ResponseEntity<ApiResponse<LoanResponse>> returnLoan(
            @Valid @RequestBody ReturnLoanRequest returnLoanRequest,
            HttpServletRequest httpServletRequest
    ) {
        LoanResponse loan = loanService.returnBook(returnLoanRequest.copyCode());

        return ok(
                "Loan returned successfully",
                loan,
                httpServletRequest
        );
    }
}
