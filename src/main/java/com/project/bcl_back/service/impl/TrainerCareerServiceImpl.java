package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerCareerRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.service.TrainerCareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerCareerServiceImpl implements TrainerCareerService {
    @Override
    public ResponseDto<TrainerCareerResponseDto> postTrainerCareer(TrainerCareerRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long id) {
        return null;
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> updateTrainerCareer(Long id, TrainerCareerRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteTrainerCareer(Long id) {
        return null;
    }
}
