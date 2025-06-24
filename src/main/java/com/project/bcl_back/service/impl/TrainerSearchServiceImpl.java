package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.response.*;
import com.project.bcl_back.entity.TrainerCareer;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.TrainerLicense;
import com.project.bcl_back.entity.UploadFile;
import com.project.bcl_back.repository.TrainerCareerRepository;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.TrainerLicenseRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.TrainerSearchService;
import jakarta.persistence.EntityNotFoundException;
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

        List<TrainerCareer> careers = trainerCareerRepository.findByTrainerInfoId(trainerId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_CAREER));

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

        List<TrainerLicense> licenses = trainerLicenseRepository.findByTrainerInfoId(trainerId)
                .orElseThrow();

        responseDtos = licenses.stream()
                .map(license -> TrainerLicenseResponseDto.builder()
                        .trainerId(license.getTrainerInfo().getId())
                        .licenseType(license.getLicenseType())
                        .licenseName(license.getLicenseName())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> getAllTrainers() {
        List<TrainerListResponseDto> responseDtos = null;

        List<TrainerInfo> trainers = trainerInfoRepository.findAllWithUserAndProfileImage();

        responseDtos =  trainers.stream()
                .map(trainer -> TrainerListResponseDto.builder()
                                .trainerId(trainer.getId())
                                .name(trainer.getUser().getName())
                                .shortIntroduce(trainer.getShortIntroduce())
                        .jobAddress(trainer.getJobAddress())
                        .profileImage(
                                trainer.getUser().getProfileImage() != null
                                        ? trainer.getUser().getProfileImage().getFullUrl()
                                        : null
                        )
                                .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<TrainerDetailResponseDto> getTrainerById(Long trainerId) {
        TrainerDetailResponseDto responseDto = null;

        TrainerInfo trainer = trainerInfoRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.TRAINER_NOT_FOUND));

        List<TrainerCareerResponseDto> careers = trainer.getTrainerCareers().stream()
                .map(career -> TrainerCareerResponseDto.builder()
                        .trainerId(career.getTrainerInfo().getId())
                        .companyName(career.getCompanyName())
                        .companyJoin(career.getCompanyJoin())
                        .companyQuit(career.getCompanyQuit())
                        .build())
                .collect(Collectors.toList());

        List<TrainerLicenseResponseDto> licenses = trainer.getTrainerLicenses().stream()
                .map(license -> TrainerLicenseResponseDto.builder()
                        .trainerId(license.getTrainerInfo().getId())
                        .licenseType(license.getLicenseType())
                        .licenseName(license.getLicenseName())
                        .build())
                .collect(Collectors.toList());

        String profileUrl = null;
        if (trainer.getUser().getProfileImage() != null) {
            UploadFile image = trainer.getUser().getProfileImage();
            profileUrl = "/files/" + image.getFileName();
        }

        responseDto = TrainerDetailResponseDto.builder()
                .trainerId(trainer.getId())
                .name(trainer.getUser().getName())
                .jobAddress(trainer.getJobAddress())
                .shortIntroduce(trainer.getShortIntroduce())
                .longIntroduce(trainer.getLongIntroduce())
                .educationName(trainer.getEducationName())
                .educationEntrance(trainer.getEducationEntrance())
                .educationGraduate(trainer.getEducationGraduate())
                .careers(careers)
                .licenses(licenses)
                .profileImage(profileUrl)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByName(String name) {
        List<TrainerListResponseDto> responseDtos = null;

        List<TrainerInfo> trainers = trainerInfoRepository.findTrainerByName(name);

        responseDtos = trainers.stream()
                .map(trainer -> TrainerListResponseDto.builder()
                        .trainerId(trainer.getId())
                        .name(trainer.getUser().getName())
                        .shortIntroduce(trainer.getShortIntroduce())
                        .jobAddress(trainer.getJobAddress())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByAddress(String jobAddress) {
        List<TrainerListResponseDto> responseDtos = null;

        List<TrainerInfo> trainers = trainerInfoRepository.findTrainerByAddress(jobAddress);

        responseDtos = trainers.stream()
                .map(trainer -> TrainerListResponseDto.builder()
                        .trainerId(trainer.getId())
                        .name(trainer.getUser().getName())
                        .shortIntroduce(trainer.getShortIntroduce())
                        .jobAddress(trainer.getJobAddress())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }
}
