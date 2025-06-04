package com.project.bcl_back.dto.user.response;

import com.project.bcl_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignUpResponseDto {
    User user;
}