package com.project.bcl_back.dto.admin.response;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter @Setter
@Builder
public class GetAllTrainersResponseDto {
    private Long trainerId;
    private String username;
    private String name;
    private LocalDate birthdate;
    private String jobAddress;
    private String createdAt;
    private TrainerStatus status;
}
