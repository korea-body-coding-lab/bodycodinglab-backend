package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerLicenseRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.entity.TrainerLicense;
import com.project.bcl_back.service.TrainerLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerLicenseServiceImpl implements TrainerLicenseService {
    @Override
    public ResponseDto<TrainerLicenseResponseDto> postTrainerLicense(TrainerLicenseRequestDto dto) {
        TrainerLicenseResponseDto responseDto = null;
        TrainerLicense newLicense = TrainerLicense.builder()
                .licenseType(dto.getLicenseType())
                .licenseName(dto.getLicenseName())
                .build();


        return null;
    }

    @Override
    public ResponseDto<List<TrainerLicenseResponseDto>> getTrainerLicense(Long id) {
        return null;
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> updateTrainerLicense(Long id, TrainerLicenseRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteTrainerLicense(Long id) {
        return null;
    }
}
