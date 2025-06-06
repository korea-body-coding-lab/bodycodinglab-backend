package com.project.bcl_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpTrainerResponseDto {
    private Long id;
    private String username;
    private String role;
    private String status;
    private String profileImageUrl;
}