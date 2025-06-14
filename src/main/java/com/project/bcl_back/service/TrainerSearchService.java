package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainerSearchService {
    ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long trainerId);

    ResponseDto<List<TrainerLicenseResponseDto>> getTrainerLicense(Long trainerId);

    ResponseDto<List<TrainerListResponseDto>> getAllTrainers();

    ResponseDto<TrainerDetailResponseDto> getTrainerById(Long trainerId);

    ResponseDto<List<TrainerListResponseDto>> searchTrainerByName(String name);

    ResponseDto<List<TrainerListResponseDto>> searchTrainerByAddress(String jobAddress);
}
