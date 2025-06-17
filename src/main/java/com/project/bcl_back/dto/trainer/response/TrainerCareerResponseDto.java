package com.project.bcl_back.dto.trainer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class TrainerCareerResponseDto {
    private Long id;
    private Long trainerId;
    private String companyName;
    private LocalDate companyJoin;
    private LocalDate companyQuit;
}
