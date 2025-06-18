package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerLicenseRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentLicenseResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface TrainerLicenseService {
    ResponseDto<TrainerLicenseResponseDto> postTrainerLicense(Long id,  @ModelAttribute TrainerLicenseRequestDto dto, MultipartFile file) throws IOException;

    ResponseDto<TrainerLicenseResponseDto> updateTrainerLicense(Long id, @ModelAttribute TrainerLicenseRequestDto dto, MultipartFile file) throws IOException;

    ResponseDto<TrainerLicenseResponseDto> deleteTrainerLicense(Long id, Long licenseId);

    ResponseDto<Void> deleteAllTrainerLicense(Long id);

    ResponseDto<TrainerRecentLicenseResponseDto> getRecentLicense(Long id);

    ResponseDto<List<TrainerLicenseResponseDto>> getLicenseList(Long id);
}
