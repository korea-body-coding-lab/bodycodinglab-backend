package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerLicenseRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentLicenseResponseDto;
import com.project.bcl_back.entity.TrainerCareer;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.TrainerLicense;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.TrainerLicenseRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.TrainerLicenseService;
import com.project.bcl_back.service.UploadFileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerLicenseServiceImpl implements TrainerLicenseService {
    private final TrainerLicenseRepository trainerLicenseRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final UserRepository userRepository;
    private final UploadFileService uploadFileService;

    @Override
    public ResponseDto<TrainerLicenseResponseDto> postTrainerLicense(Long id, TrainerLicenseRequestDto dto, MultipartFile file)
        throws IOException
    {
        TrainerLicenseResponseDto responseDto = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerLicense license = TrainerLicense.create(trainer,
                dto.getLicenseType(),
                dto.getLicenseName());

        TrainerLicense saved = trainerLicenseRepository.save(license);

        if (file != null && !file.isEmpty()) {
                uploadFileService.saveFile(file, user.getTrainerInfo().getId(), TargetType.LICENSE);
            trainerLicenseRepository.save(license);
        }

        responseDto = TrainerLicenseResponseDto.builder()
                .id(saved.getId())
                .trainerId(saved.getTrainerInfo().getId())
                .licenseType(saved.getLicenseType())
                .licenseName(saved.getLicenseName())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> updateTrainerLicense(Long id, TrainerLicenseRequestDto dto, MultipartFile file) throws IOException {
        TrainerLicenseResponseDto responseDto = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerLicense license = trainerLicenseRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LICENSE));

        license.setLicenseType(dto.getLicenseType());
        license.setLicenseName(dto.getLicenseName());

        TrainerLicense updateLicense = trainerLicenseRepository.save(license);

        if (file != null && !file.isEmpty()) {
            uploadFileService.saveFile(file, user.getTrainerInfo().getId(), TargetType.LICENSE);
            trainerLicenseRepository.save(license);
        }

        responseDto = TrainerLicenseResponseDto.builder()
                .id(updateLicense.getId())
                .trainerId(updateLicense.getTrainerInfo().getId())
                .licenseType(updateLicense.getLicenseType())
                .licenseName(updateLicense.getLicenseName())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> deleteTrainerLicense(Long id, Long licenseId) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerLicense license = trainerLicenseRepository.findById(licenseId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LICENSE));

        trainerLicenseRepository.delete(license);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);
    }

    @Override
    public ResponseDto<Void> deleteAllTrainerLicense(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        List<TrainerLicense> license = trainerLicenseRepository.findByTrainerInfoId(trainer.getId())
                .orElseThrow();

        trainerLicenseRepository.deleteAll(license);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);
    }


    @Override
    public ResponseDto<List<TrainerLicenseResponseDto>> getLicenseList(Long id) {

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        List<TrainerLicense> licenses = trainerLicenseRepository.findByTrainerInfoId(trainer.getId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LICENSE));

        List<TrainerLicenseResponseDto> responseDto = licenses.stream()
                .map(license -> TrainerLicenseResponseDto.builder()
                        .id(license.getId())
                        .trainerId(license.getTrainerInfo().getId())
                        .licenseType(license.getLicenseType())
                        .licenseName(license.getLicenseName())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<TrainerRecentLicenseResponseDto> getRecentLicense(Long id) {
        TrainerRecentLicenseResponseDto responseDto = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerLicense license = trainerLicenseRepository.findTopByTrainerInfoIdOrderByIdDesc(trainer.getId());

        responseDto = TrainerRecentLicenseResponseDto.builder()
                .licenseType(license.getLicenseType())
                .licenseName(license.getLicenseName())
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

}
