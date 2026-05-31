package com.colton.library_api.service;

import com.colton.library_api.dto.loan.LoanRequest;
import com.colton.library_api.dto.loan.LoanResponse;
import com.colton.library_api.exception.BookAlreadyLoanedException;
import com.colton.library_api.exception.MemberInactiveException;
import com.colton.library_api.exception.NoActiveLoanException;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.BookCopy;
import com.colton.library_api.model.Loan;
import com.colton.library_api.model.Member;
import com.colton.library_api.repository.BookCopyRepository;
import com.colton.library_api.repository.LoanRepository;
import com.colton.library_api.repository.MemberRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookCopyRepository bookCopyRepository;

    public LoanService(LoanRepository loanRepository,
                       MemberRepository memberRepository,
                       BookCopyRepository bookCopyRepository
    ) {
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    private LoanResponse mapToResponse(Loan loan) {
        return new LoanResponse(
            loan.getId(),
            loan.getMember().getDisplayName(),
            loan.getCopy().getBook().getTitle(),
            loan.getCopy().getCopyCode(),
            loan.getLoanDate(),
            loan.getDueDate(),
            loan.getReturnDate()
        );
    }

    public List<LoanResponse> findAll() {
        return loanRepository.findAll(Sort.by(Sort.Direction.DESC, "loanDate"))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Member findMemberByCode(String memberCode) {
        return memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found with code: " + memberCode));
    }

    private BookCopy findCopyByCode(String copyCode) {
        return bookCopyRepository.findByCopyCode(copyCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Copy not found with code: " + copyCode));
    }

    private Loan findByIdEntity(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
    }

    public LoanResponse findById(Long id) {
        return mapToResponse(findByIdEntity(id));
    }

    private LocalDate generateLoanDate() {
        return LocalDate.now();
    }

    private LocalDate generateDueDate(LocalDate loanDate) {
        return loanDate.plusWeeks(3);
    }

    private Loan getActiveLoan(BookCopy copy) {
        return loanRepository.findByCopyIdAndReturnDateIsNull(copy.getId())
                .orElseThrow(() -> new NoActiveLoanException("No active loan"));
    }

    public LoanResponse createLoan(LoanRequest loanRequest) {
        if (loanRequest == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        BookCopy copy = findCopyByCode(loanRequest.copyCode());

        if (loanRepository.existsByCopyIdAndReturnDateIsNull(copy.getId())) {
            throw new BookAlreadyLoanedException("This copy is already on loan");
        }

        Member member = findMemberByCode(loanRequest.memberCode());

        if (!member.isActive()) {
            throw new MemberInactiveException("Specified member is not active");
        }
        LocalDate loanDate = generateLoanDate();
        LocalDate dueDate = generateDueDate(loanDate);

        Loan loan = new Loan(member, copy, loanDate, dueDate);
        loanRepository.save(loan);
        return mapToResponse(loan);
    }

    public LoanResponse returnBook(LoanRequest loanRequest) {
        BookCopy copy = findCopyByCode(loanRequest.copyCode());
        Loan loan = getActiveLoan(copy);
        loan.returnBook(LocalDate.now());
        loanRepository.save(loan);
        return mapToResponse(loan);
    }

}
