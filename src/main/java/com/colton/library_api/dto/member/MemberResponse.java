package com.colton.library_api.dto.member;

public record MemberResponse(
   String memberCode,
   String first,
   String middle,
   String last,
   String email,
   boolean active
) {}
