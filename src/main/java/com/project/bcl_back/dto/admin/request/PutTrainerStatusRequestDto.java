package com.project.bcl_back.dto.admin.request;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PutTrainerStatusRequestDto {
    private Long trainerId;
    private TrainerStatus newTrainerStatus;
}
