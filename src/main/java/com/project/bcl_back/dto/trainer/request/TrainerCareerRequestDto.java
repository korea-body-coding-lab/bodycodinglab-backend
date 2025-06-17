package com.project.bcl_back.dto.trainer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class TrainerCareerRequestDto {
    private Long id;
    private String companyName;
    private LocalDate companyJoin;
    private LocalDate companyQuit;
}
