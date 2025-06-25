package com.project.bcl_back.dto.auth.response;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.common.enums.user.UserRole;
import lombok.Getter;

@Getter
public class LoginRejectedTrainerResponseDto extends LoginUserResponseDto{
    private TrainerStatus trainerStatus;

    public LoginRejectedTrainerResponseDto(String token, int exprTime, Long id, UserRole role, String username, String name, String profileImageUrl, TrainerStatus trainerStatus) {
        super(token, exprTime, id, role, username, name, profileImageUrl);
        this.trainerStatus = trainerStatus;
    }
}
