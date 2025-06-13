package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainerSearchService {
    ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long trainerId);

    ResponseDto<List<TrainerLicenseResponseDto>> getTrainerLicense(Long trainerId);
}
