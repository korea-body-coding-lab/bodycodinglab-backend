package com.project.bcl_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SignInUserResponseDto {
    private String token;
    private Long userId;
    private String name;
    byte[] profileImage;
}