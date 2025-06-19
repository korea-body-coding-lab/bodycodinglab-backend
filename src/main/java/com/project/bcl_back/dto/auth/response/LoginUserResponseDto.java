package com.project.bcl_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

@AllArgsConstructor
@Getter
@Builder
public class LoginUserResponseDto {
    private Long id;
    private String role;
    private String username;
    private String name;
    private String profileImageUrl;
    private String token;
    private int exprTime;
}