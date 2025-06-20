package com.project.bcl_back.dto.trainer.response;

import com.project.bcl_back.entity.TrainerCareer;
import com.project.bcl_back.entity.TrainerLicense;
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
public class TrainerDetailResponseDto {
    private Long trainerId;
    private String name;
    private String jobAddress;
    private String shortIntroduce;
    private String longIntroduce;
    private String educationName;
    private String educationEntrance;
    private String educationGraduate;
    private List<TrainerCareerResponseDto> careers;
    private List<TrainerLicenseResponseDto> licenses;
    private String profileImage;
}
