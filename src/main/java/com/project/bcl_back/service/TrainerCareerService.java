package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerCareerRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentCareerResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainerCareerService {
    ResponseDto<TrainerCareerResponseDto> postTrainerCareer(@Valid TrainerCareerRequestDto dto);

    ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long id);

    ResponseDto<TrainerCareerResponseDto> updateTrainerCareer(Long id, @Valid TrainerCareerRequestDto dto);

    ResponseDto<Void> deleteTrainerCareer(Long id);

    ResponseDto<TrainerRecentCareerResponseDto> getRecentCareer();
}
