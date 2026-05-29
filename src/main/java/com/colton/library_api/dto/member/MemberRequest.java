package com.colton.library_api.dto.member;

public record MemberRequest(
      String first,
      String middle,
      String last,
      String email
) {}
