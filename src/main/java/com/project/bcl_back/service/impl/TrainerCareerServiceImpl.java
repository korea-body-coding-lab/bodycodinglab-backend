package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerCareerRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentCareerResponseDto;
import com.project.bcl_back.entity.TrainerCareer;
import com.project.bcl_back.repository.TrainerCareerRepository;
import com.project.bcl_back.service.TrainerCareerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerCareerServiceImpl implements TrainerCareerService {
    private final TrainerCareerRepository trainerCareerRepository;

    @Override
    public ResponseDto<TrainerCareerResponseDto> postTrainerCareer(TrainerCareerRequestDto dto) {
        TrainerCareerResponseDto responseDto = null;
        TrainerCareer newCareer = TrainerCareer.builder()
                .companyName(dto.getCompanyName())
                .companyJoin(dto.getCompanyJoin())
                .companyQuit(dto.getCompanyQuit())
                .build();

        TrainerCareer savedCareer = trainerCareerRepository.save(newCareer);

        responseDto = TrainerCareerResponseDto.builder()
                .id(savedCareer.getId())
                .trainerId(savedCareer.getTrainerId())
                .companyName(savedCareer.getCompanyName())
                .companyJoin(savedCareer.getCompanyJoin())
                .companyQuit(savedCareer.getCompanyQuit())
                .build();

        return null;
    }

    @Override
    public ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long id) {
        List<TrainerCareerResponseDto> responseDtos = null;

//        List<TrainerCareer> careers = trainerCareerRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXISTS_CAREER));
//
//        responseDtos = careers.stream()
//                .map(career -> TrainerCareerResponseDto.builder()
//                    .id(career.getId())
//                    .trainerId(career.getTrainerId())
//                    .companyName(career.getCompanyName())
//                    .companyJoin(career.getCompanyJoin())
//                    .companyQuit(career.getCompanyQuit())
//                    .build())
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> updateTrainerCareer(Long id, TrainerCareerRequestDto dto) {
        TrainerCareerResponseDto responseDto = null;

        TrainerCareer career = trainerCareerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_CAREER));

        career.setCompanyName(dto.getCompanyName());
        career.setCompanyJoin(dto.getCompanyJoin());
        career.setCompanyQuit(dto.getCompanyQuit());

        TrainerCareer updateCareer = trainerCareerRepository.save(career);

        responseDto = TrainerCareerResponseDto.builder()
                .id(updateCareer.getId())
                .trainerId(updateCareer.getTrainerId())
                .companyName(updateCareer.getCompanyName())
                .companyJoin(updateCareer.getCompanyJoin())
                .companyQuit(updateCareer.getCompanyQuit())
                .build();

        return null;
    }

    @Override
    public ResponseDto<Void> deleteTrainerCareer(Long id) {
        TrainerCareer career = trainerCareerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_CAREER));

        career.getTrainerInfo().removeCareer(career);

        trainerCareerRepository.deleteById(id);

        return null;
    }

    @Override
    public ResponseDto<TrainerRecentCareerResponseDto> getRecentCareer() {
        return null;
    }
}
