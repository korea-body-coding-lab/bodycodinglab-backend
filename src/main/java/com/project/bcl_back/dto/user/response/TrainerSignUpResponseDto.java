package com.project.bcl_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrainerSignUpResponseDto {
    private Long id;
    private String username;
    private String role;
    private String status;
    private String profileImageUrl;
}