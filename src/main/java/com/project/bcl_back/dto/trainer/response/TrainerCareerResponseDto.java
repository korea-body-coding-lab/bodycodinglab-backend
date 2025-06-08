package com.project.bcl_back.dto.trainer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class TrainerCareerResponseDto {
    private String companyName;
    private Date companyJoin;
    private Date companyQuit;
}
