package com.project.bcl_back.dto.trainer.request;

import com.project.bcl_back.common.enums.trainerLicense.LicenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrainerLicenseRequestDto {
    private LicenseType licenseType;
    private String licenseName;
}
