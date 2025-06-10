package com.project.bcl_back.dto.admin.request;

import com.project.bcl_back.common.enums.trainerInfo.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateTrainerStatusRequestDto {
    private Status newStatus;
    private String changeReason;
}
