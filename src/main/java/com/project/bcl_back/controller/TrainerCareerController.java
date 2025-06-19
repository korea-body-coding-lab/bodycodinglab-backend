package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerCareerRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentCareerResponseDto;
import com.project.bcl_back.service.TrainerCareerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.USER_API)
@RequiredArgsConstructor
public class TrainerCareerController {
    private final TrainerCareerService trainerCareerService;

    private static final String POST_TRAINER_CAREER = "/trainers/me/information/career";
    private static final String PUT_TRAINER_CAREER = "/trainers/me/information/career";
    private static final String DELETE_TRAINER_CAREER = "/trainers/me/information/career/{careerId}";
    private static final String DELETE_ALL_TRAINER_CAREER = "/trainers/me/information/career/all";
    private static final String GET_TRAINER_CAREER_LIST = "/trainers/me/information/career";
    private static final String GET_RECENT_TRAINER_CAREER = "/trainers/me/information/career/recent";

    // 트레이너 경력 생성
    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(POST_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> postTrainerCareer(
            @AuthenticationPrincipal Long id,
            @Valid @RequestBody TrainerCareerRequestDto dto
    ) {
        ResponseDto<TrainerCareerResponseDto> response = trainerCareerService.postTrainerCareer(id, dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 트레이너 경력 수정
    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(PUT_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> updateTrainerCareer(
            @AuthenticationPrincipal Long id,
            @Valid @RequestBody TrainerCareerRequestDto dto
    ) {
        ResponseDto<TrainerCareerResponseDto> response = trainerCareerService.updateTrainerCareer(id, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 경력 삭제(단건)
    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping(DELETE_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> deleteTrainerCareer(
            @AuthenticationPrincipal Long id,
            @PathVariable Long careerId
    ) {
        ResponseDto<TrainerCareerResponseDto> response = trainerCareerService.deleteTrainerCareer(id, careerId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 경력 삭제(전체)
    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping(DELETE_ALL_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<Void>> deleteAllTrainerCareer(@AuthenticationPrincipal Long id) {
        ResponseDto<Void> response = trainerCareerService.deleteAllTrainerCareer(id);
        return ResponseEntity.noContent().build();
    }

    // 트레이너 현재 경력 목록 조회
    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(GET_TRAINER_CAREER_LIST)
    public ResponseEntity<ResponseDto<List<TrainerCareerResponseDto>>> getCareerList(
            @AuthenticationPrincipal Long id
    ){
        ResponseDto<List<TrainerCareerResponseDto>> response = trainerCareerService.getCareerList(id);
        return  ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 최근 경력 조회
    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(GET_RECENT_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerRecentCareerResponseDto>> getRecentCareer(
            @AuthenticationPrincipal Long id
    ){
        ResponseDto<TrainerRecentCareerResponseDto> response = trainerCareerService.getRecentCareer(id);
        return  ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

}
