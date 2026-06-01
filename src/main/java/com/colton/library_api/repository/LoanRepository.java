package com.colton.library_api.repository;

import com.colton.library_api.model.Loan;
import com.colton.library_api.model.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByCopyIdAndReturnDateIsNull(Long copyId);
    boolean existsByCopyId(Long copyId);
    boolean existsByCopyIdAndReturnDateIsNull(Long copyId);
    boolean existsByMemberIdAndReturnDateIsNull(Long memberId);
    List<Loan> findByMemberId(Long memberId);
    List<Loan> findByMemberMemberCode(String memberCode);
    List<Loan> findByReturnDateIsNull(Sort sort);
    List<Loan> findByReturnDateIsNotNull(Sort sort);
}