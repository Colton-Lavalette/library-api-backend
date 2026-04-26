package com.colton.library_api.repository;

import com.colton.library_api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findByNameLast(String last);
    List<Member> findByNameFirstAndNameLast(String first, String last);
}