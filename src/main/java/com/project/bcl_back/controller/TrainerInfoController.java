package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.ReapplyTrainerRequestDto;
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
@RequestMapping(ApiMappingPattern.TRAINER_API)
@RequiredArgsConstructor
public class TrainerInfoController {
    private final TrainerInfoService trainerInfoService;

    private static final String UPDATE_TRAINER_INFO = "/me/information";
    private static final String TRAINER_REAPPLY = "/me/reapply";

    // 트래이너 정보 수정
    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(UPDATE_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerInfoResponseDto>> updateTrainerInfo (
            @AuthenticationPrincipal Long id,
            @ModelAttribute TrainerInfoRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        ResponseDto<TrainerInfoResponseDto> response = trainerInfoService.updateTrainerInfo(id, dto, files);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    @PutMapping(TRAINER_REAPPLY)
    public ResponseEntity<ResponseDto<Void>> reapplyTrainer(
            @AuthenticationPrincipal Long id,
            @Valid @RequestPart(value = "dto") ReapplyTrainerRequestDto dto,
            @RequestPart(value = "attachmentFile") MultipartFile attachmentFile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerInfoService.reapplyTrainer(id, dto, attachmentFile));
    }

//    @PreAuthorize("hasRole('TRAINER')")
//    @GetMapping(UPDATE_TRAINER_INFO)
//    public ResponseEntity<ResponseDto<TrainerStatus>> getTrainerStatus (@AuthenticationPrincipal Long id) {
//        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
//    }
}