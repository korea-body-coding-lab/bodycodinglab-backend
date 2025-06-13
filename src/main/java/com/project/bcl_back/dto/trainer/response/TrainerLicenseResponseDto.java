package com.project.bcl_back.dto.trainer.response;

import com.project.bcl_back.common.enums.trainerLicense.LicenseType;
import com.project.bcl_back.dto.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrainerLicenseResponseDto {
    private Long trainerId;
    private LicenseType licenseType;
    private String licenseName;
}
