package com.project.bcl_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SignUpMemberResponseDto {
    private Long id;
    private String username;
}