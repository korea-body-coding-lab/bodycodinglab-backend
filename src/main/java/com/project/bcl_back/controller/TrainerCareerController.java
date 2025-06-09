package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerCareerRequestDto;
import com.project.bcl_back.dto.trainer.request.TrainerInfoRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerInfoResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentLicenseResponseDto;
import com.project.bcl_back.entity.TrainerCareer;
import com.project.bcl_back.service.TrainerCareerService;
import com.project.bcl_back.service.TrainerInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_API)
@RequiredArgsConstructor
public class TrainerCareerController {
    private final TrainerCareerService trainerCareerService;

    private static final String POST_TRAINER_CAREER = "/me/information/career";
    private static final String GET_TRAINER_CAREER = "/me/information/career";
    private static final String PUT_TRAINER_CAREER = "/me/information/career";
    private static final String DELETE_TRAINER_CAREER = "/me/information/career";
    private static final String GER_RECENT_TRAINER_CAREER = "/me/information/career/recent";

    // 트레이너 경력 생성
    @PostMapping(POST_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> postTrainerCareer(
            @Valid @RequestBody TrainerCareerRequestDto dto
    ) {
        ResponseDto<TrainerCareerResponseDto> response = trainerCareerService.postTrainerCareer(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 트레이너 경력 조회(단건)
    @GetMapping(GET_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<List<TrainerCareerResponseDto>>> getTrainerCareer(
            @PathVariable Long id
    ) {
        ResponseDto<List<TrainerCareerResponseDto>> response = trainerCareerService.getTrainerCareer(id);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
    // 트레이너 경력 수정
    @PutMapping(PUT_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> updateTrainerCareer(
            @PathVariable Long id,
            @Valid @RequestBody TrainerCareerRequestDto dto
    ) {
        ResponseDto<TrainerCareerResponseDto> response = trainerCareerService.updateTrainerCareer(id, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 경력 삭제
    @DeleteMapping(DELETE_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<Void>> deleteTrainerCareer(@PathVariable Long id) {
        ResponseDto<Void> response = trainerCareerService.deleteTrainerCareer(id);
        return ResponseEntity.noContent().build();
    }

    // 트레이너 최근 경력 조회
    @GetMapping(GER_RECENT_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerRecentCareerResponseDto>> getRecentCareer(){
        ResponseDto<TrainerRecentCareerResponseDto> response = trainerCareerService.getRecentCareer();
        return  ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

}
