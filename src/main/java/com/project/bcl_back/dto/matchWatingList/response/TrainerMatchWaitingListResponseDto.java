package com.project.bcl_back.dto.matchWatingList.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrainerMatchWaitingListResponseDto {
    private Long trainerId;
    private String trainerName;
    private String trainerJobAddress;
    private LocalDateTime appliedDate;
}
