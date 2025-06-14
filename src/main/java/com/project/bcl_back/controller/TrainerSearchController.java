package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.response.*;
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
    private static final String GET_ALL_TRAINER_INFO = "/trainer-list";
    private static final String GET_TRAINER_INFO = "/{trainerId}";
    private static final String GET_TRAINER_BY_NAME = "/search-name";
    private static final String GET_TRAINER_BY_ADDRESS = "/search-address";

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

    // 트레이너 조회(전체)
    @GetMapping(GET_ALL_TRAINER_INFO)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> getAllTrainers() {
        ResponseDto<List<TrainerListResponseDto>> trainers = trainerSearchService.getAllTrainers();
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainers);
    }

    // 트레이너 단건 조회
    @GetMapping(GET_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerDetailResponseDto>> getTrainerById(
            @PathVariable Long trainerId
    ){
        ResponseDto<TrainerDetailResponseDto> trainer = trainerSearchService.getTrainerById(trainerId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainer);
    }

    // 트레이너 정보 조회(이름)
    @GetMapping(GET_TRAINER_BY_NAME)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> searchTrainerByName(
            @RequestParam String name
    ){
        ResponseDto<List<TrainerListResponseDto>> response = trainerSearchService.searchTrainerByName(name);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 정보 조회(근무지 주소)
    @GetMapping(GET_TRAINER_BY_ADDRESS)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> searchTrainerByAddress(
            @RequestParam String jobAddress
    ){
        ResponseDto<List<TrainerListResponseDto>> response = trainerSearchService.searchTrainerByAddress(jobAddress);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}
