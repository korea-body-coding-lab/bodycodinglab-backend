package com.project.bcl_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInUserResponseDto {
    private String token;
    private Long userId;
    private String role;
    private String name;
    private String profileImageUrl;
}