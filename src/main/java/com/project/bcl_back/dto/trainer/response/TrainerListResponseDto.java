package com.project.bcl_back.dto.trainer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrainerListResponseDto {
    private Long trainerId;
    private String name;
    private String shortIntroduce;
    private String jobAddress;
}
