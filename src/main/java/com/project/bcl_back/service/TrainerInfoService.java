package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerInfoRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerListResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerInfoResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainerInfoService {
    ResponseDto<TrainerInfoResponseDto> postTrainerInfo(Long id, @Valid TrainerInfoRequestDto dto);

    ResponseDto<TrainerInfoResponseDto> updateTrainerInfo(Long id, @Valid TrainerInfoRequestDto dto);
}
