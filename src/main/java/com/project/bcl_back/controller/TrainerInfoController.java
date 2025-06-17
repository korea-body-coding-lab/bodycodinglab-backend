package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerInfoRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerListResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerInfoResponseDto;
import com.project.bcl_back.service.TrainerInfoService;
import jakarta.validation.Valid;
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
@RequestMapping(ApiMappingPattern.USER_API)
@RequiredArgsConstructor
public class TrainerInfoController {
    private final TrainerInfoService trainerInfoService;

    private static final String POST_TRAINER_INFO = "/trainers/me/information";
    private static final String UPDATE_TRAINER_INFO = "/trainers/me/information/update";


    // 트레이너 정보 생성
    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(POST_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerInfoResponseDto>> postTrainerInfo(
            @AuthenticationPrincipal Long id,
            @Valid @RequestBody TrainerInfoRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<TrainerInfoResponseDto> response = trainerInfoService.postTrainerInfo(id, dto, file);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 트래이너 정보 수정
    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(UPDATE_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerInfoResponseDto>> updateTrainerInfo (
            @AuthenticationPrincipal Long id,
            @Valid @RequestBody TrainerInfoRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<TrainerInfoResponseDto> response = trainerInfoService.updateTrainerInfo(id, dto, file);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}