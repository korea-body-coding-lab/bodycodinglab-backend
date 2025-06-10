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
    private String username;
    private String name;
    private int age;
    private Gender gender;
    private TrainerStatus trainerStatus;
}
