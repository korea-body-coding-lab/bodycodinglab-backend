package com.project.bcl_back.dto.user.response;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
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
    private TrainerStatus trainerStatus;
}
