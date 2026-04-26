package com.colton.library_api.repository;

import com.colton.library_api.model.Loan;
import com.colton.library_api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByMemberId(Long memberId);
    List<Loan> findByReturnDateIsNull();
    List<Loan> findByReturnDateIsNotNull();
    List<Loan> findByDueDateBefore(LocalDate date);
    Optional<Loan> findByCopyIdAndReturnDateIsNull(Long copyId);
}