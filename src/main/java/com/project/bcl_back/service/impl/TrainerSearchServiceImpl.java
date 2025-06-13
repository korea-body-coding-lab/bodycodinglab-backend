package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.entity.TrainerCareer;
import com.project.bcl_back.entity.TrainerLicense;
import com.project.bcl_back.repository.TrainerCareerRepository;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.TrainerLicenseRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.TrainerSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerSearchServiceImpl implements TrainerSearchService {
    private final TrainerCareerRepository trainerCareerRepository;
    private final TrainerLicenseRepository trainerLicenseRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long trainerId) {
        List<TrainerCareerResponseDto> responseDtos = null;

        List<TrainerCareer> careers = trainerCareerRepository.findByTrainerInfoId(trainerId);

        responseDtos = careers.stream()
                .map(career -> TrainerCareerResponseDto.builder()
                        .trainerId(career.getTrainerInfo().getId())
                        .companyName(career.getCompanyName())
                        .companyJoin(career.getCompanyJoin())
                        .companyQuit(career.getCompanyQuit())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<TrainerLicenseResponseDto>> getTrainerLicense(Long trainerId) {
        List<TrainerLicenseResponseDto> responseDtos = null;

        List<TrainerLicense> licenses = trainerLicenseRepository.findByTrainerInfoId(trainerId);

        responseDtos = licenses.stream()
                .map(license -> TrainerLicenseResponseDto.builder()
                        .trainerId(license.getTrainerInfo().getId())
                        .licenseType(license.getLicenseType())
                        .licenseName(license.getLicenseName())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }
}
