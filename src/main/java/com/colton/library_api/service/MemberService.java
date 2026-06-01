package com.colton.library_api.service;

import com.colton.library_api.dto.member.MemberResponse;
import com.colton.library_api.dto.member.MemberRequest;
import com.colton.library_api.exception.EmailAlreadyInUseException;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.Member;
import com.colton.library_api.model.Name;
import com.colton.library_api.repository.LoanRepository;
import com.colton.library_api.repository.MemberRepository;
import com.colton.library_api.util.CodeGenerator;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public MemberService(MemberRepository memberRepository,
                         LoanRepository loanRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    private MemberResponse mapToResponse(Member member) {
        return new MemberResponse(
                member.getMemberCode(),
                member.getName().getFirst(),
                member.getName().getMiddle(),
                member.getName().getLast(),
                member.getEmail(),
                member.isActive()
        );
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll(Sort.by(
                        Sort.Order.asc("name.last"),
                        Sort.Order.asc("name.first")
                ))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<MemberResponse> findActiveMembers() {
        return memberRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Member findByCodeEntity(String memberCode) {
        return memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with code: " + memberCode));
    }

    public MemberResponse findByCode(String memberCode) {
        return mapToResponse(findByCodeEntity(memberCode));
    }

    public MemberResponse createMember(MemberRequest memberRequest) {
        if (memberRequest == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        String memberCode = generateUniqueMemberCode();

        Name name = new Name(
                memberRequest.first(),
                memberRequest.middle(),
                memberRequest.last()
        );

        Member member = new Member(memberCode, name, memberRequest.email());
        memberRepository.save(member);

        return mapToResponse(member);
    }

    @Transactional
    public MemberResponse updateMember(String memberCode, MemberRequest memberRequest) {
        Member member = findByCodeEntity(memberCode);

        if (memberRequest.first() != null || memberRequest.last() != null || memberRequest.middle() != null) {
            Name current = member.getName();
            Name updatedName = new Name(
                memberRequest.first() != null ? memberRequest.first() : current.getFirst(),
                memberRequest.middle() != null ? memberRequest.middle() : current.getMiddle(),
                memberRequest.last() != null ? memberRequest.last() : current.getLast()
            );
            member.updateName(updatedName);
        }
        if (memberRequest.email() != null &&
                !memberRequest.email().equals(member.getEmail())) {

            if (memberRepository.findByEmail(memberRequest.email()).isPresent()) {
                throw new EmailAlreadyInUseException("Email already in use");
            }

            member.updateEmail(memberRequest.email());
        }

        return mapToResponse(member);
    }

    public MemberResponse deactivateMember(String memberCode) {
        Member member = findByCodeEntity(memberCode);
        if (!member.isActive()) {
            return mapToResponse(member);
        }

        member.deactivate();
        memberRepository.save(member);

        return mapToResponse(member);
    }

    public MemberResponse activateMember(String memberCode) {
        Member member = findByCodeEntity(memberCode);

        if (member.isActive()) {
            return mapToResponse(member);
        }

        member.activate();
        memberRepository.save(member);

        return mapToResponse(member);
    }

    private String generateUniqueMemberCode() {
        String memberCode;
        do {
            memberCode = CodeGenerator.generateMemberCode();
        } while (memberRepository.existsByMemberCode(memberCode));
        return memberCode;
    }
}
