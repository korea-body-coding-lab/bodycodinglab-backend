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
    ResponseDto<TrainerCareerResponseDto> postTrainerCareer(Long id, @Valid TrainerCareerRequestDto dto);

    ResponseDto<TrainerCareerResponseDto> updateTrainerCareer(Long id, @Valid TrainerCareerRequestDto dto);

    ResponseDto<Void> deleteAllTrainerCareer(Long id);

    ResponseDto<TrainerRecentCareerResponseDto> getRecentCareer(Long id);

    ResponseDto<TrainerCareerResponseDto> deleteTrainerCareer(Long id, Long careerId);

    ResponseDto<List<TrainerCareerResponseDto>> getCareerList(Long id);
}
