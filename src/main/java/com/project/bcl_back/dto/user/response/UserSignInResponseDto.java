package com.project.bcl_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignInResponseDto {

    private String userId;     // 로그인한 사용자 ID
    private String nickname;   // 사용자 닉네임
}