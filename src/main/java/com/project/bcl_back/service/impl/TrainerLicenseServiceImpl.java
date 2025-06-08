package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerLicenseRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentLicenseResponseDto;
import com.project.bcl_back.entity.TrainerLicense;
import com.project.bcl_back.repository.TrainerLicenseRepository;
import com.project.bcl_back.service.TrainerLicenseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerLicenseServiceImpl implements TrainerLicenseService {
    private final TrainerLicenseRepository trainerLicenseRepository;

    @Override
    public ResponseDto<TrainerLicenseResponseDto> postTrainerLicense(TrainerLicenseRequestDto dto) {
        TrainerLicenseResponseDto responseDto = null;
        TrainerLicense newLicense = TrainerLicense.builder()
                .licenseType(dto.getLicenseType())
                .licenseName(dto.getLicenseName())
                .build();

        TrainerLicense saved = trainerLicenseRepository.save(newLicense);

        responseDto = TrainerLicenseResponseDto.builder()
                .id(saved.getId())
                .trainerId(saved.getTrainerId())
                .licenseType(saved.getLicenseType())
                .licenseName(saved.getLicenseName())
                .build();

        return null;
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<List<TrainerLicenseResponseDto>> getTrainerLicense(Long id) {
        List<TrainerLicenseResponseDto> responseDto = null;

//        List<TrainerLicense> licenses = trainerLicenseRepository.findById(id)
//                .orElse();

//        responseDto = licenses.stream()
//                .map(license -> TrainerLicenseResponseDto.builder()
//                        .id(license.getId())
//                        .trainerId(license.getTrainerId())
//                        .licenseType(license.getLicenseType())
//                        .licenseName(license.getLicenseName())
//                        .build())
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> updateTrainerLicense(Long id, TrainerLicenseRequestDto dto) {
        TrainerLicenseResponseDto responseDto = null;

        TrainerLicense license = trainerLicenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LICENSE));

        license.setLicenseType(dto.getLicenseType());
        license.setLicenseName(dto.getLicenseName());

        TrainerLicense updateLicense = trainerLicenseRepository.save(license);

        responseDto = TrainerLicenseResponseDto.builder()
                .id(updateLicense.getId())
                .trainerId(updateLicense.getTrainerId())
                .licenseType(updateLicense.getLicenseType())
                .licenseName(updateLicense.getLicenseName())
                .build();

        return null;
    }

    @Override
    public ResponseDto<Void> deleteTrainerLicense(Long id) {
//        if (!trainerLicenseRepository.existsById(id)) {
//            throw new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LICENSE + id);
//        }

        TrainerLicense license = trainerLicenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LICENSE));

        license.getTrainerInfo().removeLicense(license);

        trainerLicenseRepository.deleteById(id);
        return null;
    }

    @Override
    public ResponseDto<TrainerRecentLicenseResponseDto> getRecentLicense() {

        return null;
    }
}
