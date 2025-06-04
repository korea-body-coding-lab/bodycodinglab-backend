package com.project.bcl_back.dto.trainer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class TrainerInfoRequestDto {
    private String jobAddress;
    private String shortIntroduce;
    private String longIntroduce;
    private String educationName;
    private Date educationEntrance;
    private Date educationGraduate;
}
