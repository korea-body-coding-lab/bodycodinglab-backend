package com.project.bcl_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetUserInfoResponseDto {
    private Long id;
    private String role;
    private String username;
    private String name;
    private String profileImageUrl;
}
