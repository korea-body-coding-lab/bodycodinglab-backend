package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.service.TrainerSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_API)
@RequiredArgsConstructor
public class TrainerSearchController {
    private final TrainerSearchService trainerSearchService;

    private static final String GET_TRAINER_CAREER = "/career";
    private static final String GET_TRAINER_LICENSE = "/license";

    // 트레이너 경력 조회
    @GetMapping(GET_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<List<TrainerCareerResponseDto>>> getTrainerCareer(
            @RequestParam Long trainerId
    ) {
        ResponseDto<List<TrainerCareerResponseDto>> response = trainerSearchService.getTrainerCareer(trainerId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 자격증 조회
    @GetMapping(GET_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<List<TrainerLicenseResponseDto>>> getTrainerLicense(
            @RequestParam Long trainerId
    ) {
        ResponseDto<List<TrainerLicenseResponseDto>> response = trainerSearchService.getTrainerLicense(trainerId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}
