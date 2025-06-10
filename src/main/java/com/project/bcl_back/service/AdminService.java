package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerResponseDto;
import com.project.bcl_back.dto.user.request.UpdateTrainerRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdminService {
    ResponseDto<List<GetAllTrainersResponseDto>> getAllTrainers();
    ResponseDto<GetTrainerResponseDto> getTrainer(Long trainerId);
    ResponseDto<GetTrainerResponseDto> updateTrainerStatus(Long id, Long trainerId, @Valid UpdateTrainerStatusRequestDto dto);
}
