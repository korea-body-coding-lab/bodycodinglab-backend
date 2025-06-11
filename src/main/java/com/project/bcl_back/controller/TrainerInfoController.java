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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_API)
@RequiredArgsConstructor
public class TrainerInfoController {
    private final TrainerInfoService trainerInfoService;

    private static final String POST_TRAINER_INFO = "/me/information";
    private static final String GET_ALL_TRAINER_INFO = "/trainer-list";
    private static final String GET_TRAINER_INFO = "/{trainerId}";
    private static final String GET_TRAINER_BY_NAME = "/search-name";
    private static final String GET_TRAINER_BY_ADDRESS = "/search-address";
    private static final String PUT_TRAINER_INFO = "/me/update/information";


    // 트레이너 정보 생성
    @PutMapping(POST_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerInfoResponseDto>> postTrainerInfo(
            @Valid @RequestBody TrainerInfoRequestDto dto
    ) {
        ResponseDto<TrainerInfoResponseDto> response = trainerInfoService.postTrainerInfo(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 트레이너 조회(전체)
    @GetMapping(GET_ALL_TRAINER_INFO)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> getAllTrainers() {
        ResponseDto<List<TrainerListResponseDto>> trainers = trainerInfoService.getAllTrainers();
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainers);
    }

    // 트레이너 단건 조회
    @GetMapping(GET_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerInfoResponseDto>> getTrainerById(
            @PathVariable Long id
    ){
        ResponseDto<TrainerInfoResponseDto> trainer = trainerInfoService.getTrainerById(id);
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainer);
    }

    // 트레이너 정보 조회(이름)
    @GetMapping(GET_TRAINER_BY_NAME)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> searchTrainerByName(
            @RequestParam String trainerName
    ){
        ResponseDto<List<TrainerListResponseDto>> response = trainerInfoService.searchTrainerByName(trainerName);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트레이너 정보 조회(근무지 주소)
    @GetMapping(GET_TRAINER_BY_ADDRESS)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> searchTrainerByAddress(
            @RequestParam String address
    ){
        ResponseDto<List<TrainerListResponseDto>> response = trainerInfoService.searchTrainerByAddress(address);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 트래이너 정보 수정
    @PutMapping(PUT_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerInfoResponseDto>> updateTrainerInfo (
            @PathVariable Long id,
            @Valid @RequestBody TrainerInfoRequestDto dto
    ) {
        ResponseDto<TrainerInfoResponseDto> response = trainerInfoService.updateTrainerInfo(id, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}