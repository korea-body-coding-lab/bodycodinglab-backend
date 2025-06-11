package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerInfoRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerListResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerInfoResponseDto;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.TrainerInfoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerInfoServiceImpl implements TrainerInfoService {
    private final TrainerInfoRepository trainerInfoRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<TrainerInfoResponseDto> postTrainerInfo(TrainerInfoRequestDto dto) {
        TrainerInfoResponseDto responseDto = null;
        TrainerInfo newInfo = TrainerInfo.builder()
                .id(responseDto.getId())
                .jobAddress(responseDto.getJobAddress())
                .shortIntroduce(responseDto.getShortIntroduce())
                .LongIntroduce(responseDto.getLongIntroduce())
                .educationName(responseDto.getEducationName())
                .educationEntrance(responseDto.getEducationEntrance())
                .educationGraduate(responseDto.getEducationGraduate())
                .build(); // career, license (?)

        return null;
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> getAllTrainers() {
        List<TrainerListResponseDto> responseDtos = null;

        List<TrainerInfo> trainers = trainerInfoRepository.findAll();
//        User user = userRepository.findById()
//                .orElse(null);

        responseDtos =  trainers.stream()
                .map(trainer ->


                        TrainerListResponseDto.builder()
                        .id(trainer.getId())
//                        .name(trainer.user.getName())
                        .shortIntroduce(trainer.getShortIntroduce())
                        .build())
                .collect(Collectors.toList());
        return null;
    }

    @Override
    public ResponseDto<TrainerInfoResponseDto> getTrainerById(Long id) {
        TrainerInfoResponseDto responseDto = null;

        TrainerInfo trainer = trainerInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        List<TrainerCareerResponseDto> careers = trainer.getTrainerCareer().stream()
                .map(career -> TrainerCareerResponseDto.builder()
                        .id(career.getId())
                        .trainerId(career.getTrainerId())
                        .companyName(career.getCompanyName())
                        .companyJoin(career.getCompanyJoin())
                        .companyQuit(career.getCompanyQuit())
                        .build())
                .collect(Collectors.toList());

        List<TrainerLicenseResponseDto> licenses = trainer.getTrainerLicense().stream()
                .map(license -> TrainerLicenseResponseDto.builder()
                        .id(license.getId())
                        .trainerId(license.getTrainerId())
                        .licenseType(license.getLicenseType())
                        .licenseName(license.getLicenseName())
                        .build())
                .collect(Collectors.toList());

        responseDto = TrainerInfoResponseDto.builder()
                .id(trainer.getId())
                .jobAddress(trainer.getJobAddress())
                .shortIntroduce(trainer.getShortIntroduce())
                .longIntroduce(trainer.getLongIntroduce())
                .educationName(trainer.getEducationName())
                .educationEntrance(trainer.getEducationEntrance())
                .educationGraduate(trainer.getEducationGraduate())
                .careers(careers)
                .licenses(licenses)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByName(String trainerName) {
        List<TrainerListResponseDto> responseDtos = null;

//        List<TrainerInfo> trainers = trainerInfoRepository.findByName(name);
//
//        responseDtos = trainers.stream()
//                .map(trainer -> TrainerListResponseDto.builder()
//                        .id(trainer.getId())
//                        .name()
//                        .shortIntroduce(trainer.getShortIntroduce())
//                        .jobAddress(trainer.getJobAddress())
//                        .build())
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByAddress(String address) {
        List<TrainerListResponseDto> responseDtos = null;

//        List<TrainerInfo> trainers = trainerInfoRepository.findByAddress(jobAddress);
//
//        responseDtos = trainers.stream()
//                .map(trainer -> TrainerListResponseDto.builder()
//                        .id(trainer.getId())
//                        .name()
//                        .shortIntroduce(trainer.getShortIntroduce())
//                        .jobAddress(trainer.getJobAddress())
//                        .build())
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public ResponseDto<TrainerInfoResponseDto> updateTrainerInfo(Long id, TrainerInfoRequestDto dto) {


        return null;
    }
}