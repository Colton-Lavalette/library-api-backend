package com.colton.library_api.controller;

import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.dto.member.MemberRequest;
import com.colton.library_api.dto.member.MemberResponse;
import com.colton.library_api.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController extends BaseController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers(HttpServletRequest httpServletRequest) {

        List<MemberResponse> members = memberService.findAll();

        return ok(
                "Members retrieved successfully",
                members,
                httpServletRequest
        );
    }

    @GetMapping("/{memberCode}")
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(
            @PathVariable String memberCode,
            HttpServletRequest httpServletRequest
    ) {
        MemberResponse member = memberService.findByCode(memberCode);

        return ok(
                "Member retrieved successfully",
                member,
                httpServletRequest
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(
            @Valid @RequestBody MemberRequest request
    ) {

        MemberResponse member = memberService.createMember(request);

        return created(
                member,
                "/members/" + member.memberCode()
        );
    }

    @PatchMapping("/{memberCode}/activate")
    public ResponseEntity<ApiResponse<MemberResponse>> activateMember(
            @PathVariable String memberCode,
            HttpServletRequest request
    ) {
        MemberResponse response = memberService.activateMember(memberCode);

        return ok(
                "Member activated successfully",
                response,
                request
        );
    }

    @PatchMapping("/{memberCode}/deactivate")
    public ResponseEntity<ApiResponse<MemberResponse>> deactivateMember(
            @PathVariable String memberCode,
            HttpServletRequest request
    ) {
        MemberResponse response = memberService.deactivateMember(memberCode);

        return ok(
                "Member deactivated successfully",
                response,
                request
        );
    }
}
