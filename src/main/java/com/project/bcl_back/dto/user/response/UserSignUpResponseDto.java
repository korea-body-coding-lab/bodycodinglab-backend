package com.project.bcl_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpResponseDto {

    private String userId;     // 가입된 사용자 ID
    private String email;      // 이메일
    private String nickname;   // 닉네임
}