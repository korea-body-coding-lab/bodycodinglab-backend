package com.project.bcl_back.dto.auth.response;

import com.project.bcl_back.common.enums.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class LoginUserResponseDto {
    private String token;
    private int exprTime;
    private Long id;
    private UserRole role;
    private String username;
    private String name;
    private String profileImageUrl;
}