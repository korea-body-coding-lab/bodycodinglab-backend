package com.project.bcl_back.dto.trainer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class TrainerCareerRequestDto {
    private String companyName;
    private Date companyJoin;
    private Date companyQuit;
}
