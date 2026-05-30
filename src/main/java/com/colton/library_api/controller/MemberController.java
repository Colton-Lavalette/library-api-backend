package com.colton.library_api.controller;

import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.dto.member.MemberRequest;
import com.colton.library_api.dto.member.MemberResponse;
import com.colton.library_api.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{memberCode}")
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(@PathVariable String memberCode) {

        MemberResponse member = memberService.findByCode(memberCode);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        HttpStatus.OK,
                        "Member retrieved successfully",
                        member,
                        "/members/" + memberCode
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(
            @Valid @RequestBody MemberRequest request
    ) {

        MemberResponse member = memberService.createMember(request);

        ApiResponse<MemberResponse> response =
                ApiResponseFactory.success(
                        HttpStatus.CREATED,
                        "Member created successfully",
                        member,
                        "/members/" + member.memberCode()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
