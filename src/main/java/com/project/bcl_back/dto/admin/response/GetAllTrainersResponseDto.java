package com.project.bcl_back.dto.admin.response;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
@Builder
public class GetAllTrainersResponseDto {
    private Long userId;
    private Long trainerId;
    private String username;
    private String name;
    private Gender gender;
    private String createAt;
    private TrainerStatus status;
}
