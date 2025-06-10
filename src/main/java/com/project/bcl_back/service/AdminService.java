package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface AdminService {
    ResponseDto<List<GetAllTrainersResponseDto>> getAllTrainers();
    ResponseDto<GetTrainerResponseDto> getTrainer(Long trainerId);
    ResponseDto<GetTrainerResponseDto> updateTrainerStatus(Long id, Long trainerId, @Valid UpdateTrainerStatusRequestDto dto);
}
