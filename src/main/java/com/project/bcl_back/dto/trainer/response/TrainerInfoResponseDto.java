package com.project.bcl_back.dto.trainer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TrainerInfoResponseDto {
    private Long id;
    private String jobAddress;
    private String shortIntroduce;
    private String longIntroduce;
    private String educationName;
    private String educationEntrance;
    private String educationGraduate;
}
