package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerLicenseRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.service.TrainerLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_API)
@RequiredArgsConstructor
public class TrainerLicenseController {
    private final TrainerLicenseService trainerLicenseService;

    private static final String POST_TRAINER_LICENSE = "/me/information/license";
    private static final String PUT_TRAINER_LICENSE = "/me/information/license";
    private static final String DELETE_TRAINER_LICENSE = "/me/information/license/{licenseId}";
    private static final String DELETE_ALL_TRAINER_LICENSE = "/me/information/license/all";
    private static final String GET_RECENT_TRAINER_LICENSE = "/me/information/license/recent";
    private static final String GET_TRAINER_LICENSE_LIST = "/me/information/license";

    // 트레이너 자격증 생성
    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(POST_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> postTrainerLicense(
            @AuthenticationPrincipal Long id,
            @ModelAttribute TrainerLicenseRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<TrainerLicenseResponseDto> response = trainerLicenseService.postTrainerLicense(id, dto, file);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 트레이너 자격증 수정
    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(PUT_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> updateTrainerLicense(
            @AuthenticationPrincipal Long id,
            @ModelAttribute TrainerLicenseRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<TrainerLicenseResponseDto> response = trainerLicenseService.updateTrainerLicense(id, dto, file);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 자격증 삭제(단건)
    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping(DELETE_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> deleteTrainerLicense(
            @AuthenticationPrincipal Long id,
            @PathVariable Long licenseId
    ) {
        ResponseDto<TrainerLicenseResponseDto> response = trainerLicenseService.deleteTrainerLicense(id, licenseId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 자격증 삭제(전체)
    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping(DELETE_ALL_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<Void>> deleteAllTrainerLicense(@AuthenticationPrincipal Long id) {
        ResponseDto<Void> response = trainerLicenseService.deleteAllTrainerLicense(id);
        return ResponseEntity.noContent().build();
    }

    // 트레이너 현재 자격증 목록 조회
    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(GET_TRAINER_LICENSE_LIST)
    public ResponseEntity<ResponseDto<List<TrainerLicenseResponseDto>>> getLicenseList(
            @AuthenticationPrincipal Long id
    ){
        ResponseDto<List<TrainerLicenseResponseDto>> response = trainerLicenseService.getLicenseList(id);
        return  ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 최근 등록 자격증 조회
    @GetMapping(GET_RECENT_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> getRecentLicense(
            @AuthenticationPrincipal Long id
    ){
        ResponseDto<TrainerLicenseResponseDto> response = trainerLicenseService.getRecentLicense(id);
        return  ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}
