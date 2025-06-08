package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerInfoRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerListResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerInfoResponseDto;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.service.TrainerInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerInfoServiceImpl implements TrainerInfoService {
    private final TrainerInfoRepository trainerInfoRepository;

    @Override
    public ResponseDto<TrainerInfoResponseDto> postTrainerInfo(TrainerInfoRequestDto dto) {
        TrainerInfoResponseDto responseDto = null;
        TrainerInfo newInfo = TrainerInfo.builder()
                .jobAddress(responseDto.getJobAddress())
                .shortIntroduce(responseDto.getShortIntroduce())
                .LongIntroduce(responseDto.getLongIntroduce())
                .educationName(responseDto.getEducationName())
                .educationEntrance(responseDto.getEducationEntrance())
                .educationGraduate(responseDto.getEducationGraduate())
                .build(); // career, license (?)
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> getAllTrainers() {
        List<TrainerListResponseDto> responseDtos = null;

        List<TrainerInfo> trainers = trainerInfoRepository.findAll();

        responseDtos =  trainers.stream()
                .map(trainer ->
//                        User user = userRepository.findById(trainer.userId)
//                                .orElseThrow()

                        TrainerListResponseDto.builder()
                        .id(trainer.getId())
                        .name(trainer.user.getName())
                        .shortIntroduce(trainer.getShortIntroduce())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success();
    }

    @Override
    public ResponseDto<TrainerInfoResponseDto> getTrainerById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByName(String trainerName) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByAddress(String address) {
        return null;
    }

    @Override
    public ResponseDto<TrainerInfoResponseDto> updateTrainerInfo(Long id, TrainerInfoRequestDto dto) {
        return null;
    }
}