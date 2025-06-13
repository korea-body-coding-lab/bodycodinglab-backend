package com.project.bcl_back.dto.match.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrainerMatchResponseDto {
    private Long trainerId;
    private String trainerName;
    private List<String> trainerLicensesName;
}
