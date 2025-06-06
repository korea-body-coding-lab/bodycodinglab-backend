package com.project.bcl_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpMemberResponseDto {
    private Long id;
    private String username;
    private String role;
    private String profileImageUrl;
}