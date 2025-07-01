package com.project.bcl_back.service;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerDetailResponseDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface AdminService {
    ResponseDto<Page<GetAllTrainersResponseDto>> getAllTrainers(int page, int size, TrainerStatus status);
    ResponseDto<GetTrainerDetailResponseDto> getTrainerDetail(Long trainerId);
    ResponseDto<GetTrainerDetailResponseDto> updateTrainerStatus(Long id, Long trainerId, @Valid UpdateTrainerStatusRequestDto dto) throws MessagingException;
}
