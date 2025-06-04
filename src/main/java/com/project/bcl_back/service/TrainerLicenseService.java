package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerLicenseRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainerLicenseService {
    ResponseDto<TrainerLicenseResponseDto> postTrainerLicense(@Valid TrainerLicenseRequestDto dto);

    ResponseDto<List<TrainerLicenseResponseDto>> getTrainerLicense(Long id);

    ResponseDto<TrainerLicenseResponseDto> updateTrainerLicense(Long id, @Valid TrainerLicenseRequestDto dto);

    ResponseDto<Void> deleteTrainerLicense(Long id);
}
