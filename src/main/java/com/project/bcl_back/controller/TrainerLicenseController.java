package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerCareerRequestDto;
import com.project.bcl_back.dto.trainer.request.TrainerLicenseRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentLicenseResponseDto;
import com.project.bcl_back.service.TrainerCareerService;
import com.project.bcl_back.service.TrainerLicenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_API)
@RequiredArgsConstructor
public class TrainerLicenseController {
    private final TrainerLicenseService trainerLicenseService;

    private static final String POST_TRAINER_LICENSE = "/me/information/license";
    private static final String GET_TRAINER_LICENSE = "/me/information/license";
    private static final String PUT_TRAINER_LICENSE = "/me/information/license";
    private static final String DELETE_TRAINER_LICENSE = "/me/information/license";
    private static final String GER_RECENT_TRAINER_LICENSE = "/me/information/license/recent";

    // 트레이너 자격증 생성
    @PostMapping(POST_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> postTrainerLicense(
            @Valid @RequestBody TrainerLicenseRequestDto dto
    ) {
        ResponseDto<TrainerLicenseResponseDto> response = trainerLicenseService.postTrainerLicense(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 트레이너 자격증 조회(단건)
    @GetMapping(GET_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<List<TrainerLicenseResponseDto>>> getTrainerLicense(
            @PathVariable Long id
    ) {
        ResponseDto<List<TrainerLicenseResponseDto>> response = trainerLicenseService.getTrainerLicense(id);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
    // 트레이너 자격증 수정
    @PutMapping(PUT_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> updateTrainerLicense(
            @PathVariable Long id,
            @Valid @RequestBody TrainerLicenseRequestDto dto
    ) {
        ResponseDto<TrainerLicenseResponseDto> response = trainerLicenseService.updateTrainerLicense(id, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 자격증 삭제
    @DeleteMapping(DELETE_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<Void>> deleteTrainerLicense(@PathVariable Long id) {
        ResponseDto<Void> response = trainerLicenseService.deleteTrainerLicense(id);
        return ResponseEntity.noContent().build();
    }

    // 트레이너 최근 자격증 조회
    @GetMapping(GER_RECENT_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerRecentLicenseResponseDto>> getRecentLicense(){
        ResponseDto<TrainerRecentLicenseResponseDto> response = trainerLicenseService.getRecentLicense();
        return  ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}
