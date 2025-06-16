package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerDetailResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AdminService {
    ResponseDto<List<GetAllTrainersResponseDto>> getAllTrainers();
    ResponseDto<GetTrainerDetailResponseDto> getTrainer(Long trainerId);
    Mono<ResponseEntity<ResponseDto<GetTrainerDetailResponseDto>>> updateTrainerStatus(Long id, Long trainerId, @Valid UpdateTrainerStatusRequestDto dto);
}
