package com.project.bcl_back.dto.trainer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    private Date educationEntrance;
    private Date educationGraduate;
    private List<TrainerCareerResponseDto> careers;
    private List<TrainerLicenseResponseDto> licenses;
}
