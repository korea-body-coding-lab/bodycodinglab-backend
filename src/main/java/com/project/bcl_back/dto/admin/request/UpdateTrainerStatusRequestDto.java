package com.project.bcl_back.dto.admin.request;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateTrainerStatusRequestDto {
    @NotNull(message = "상태를 선택해 주세요.")
    private TrainerStatus newStatus;
    private String changeReason;
}
