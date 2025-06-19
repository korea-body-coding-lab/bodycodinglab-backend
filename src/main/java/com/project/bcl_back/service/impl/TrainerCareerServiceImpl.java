package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerCareerRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerRecentCareerResponseDto;
import com.project.bcl_back.entity.TrainerCareer;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.TrainerLicense;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.TrainerCareerRepository;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.TrainerCareerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerCareerServiceImpl implements TrainerCareerService {
    private final TrainerCareerRepository trainerCareerRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<TrainerCareerResponseDto> postTrainerCareer(Long id, TrainerCareerRequestDto dto) {
        TrainerCareerResponseDto responseDto = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerCareer career = TrainerCareer.create(trainer,
                dto.getCompanyName(),
                dto.getCompanyJoin(),
                dto.getCompanyQuit());

        TrainerCareer savedCareer = trainerCareerRepository.save(career);

        responseDto = TrainerCareerResponseDto.builder()
                .id(savedCareer.getId())
                .trainerId(savedCareer.getTrainerInfo().getId())
                .companyName(savedCareer.getCompanyName())
                .companyJoin(savedCareer.getCompanyJoin())
                .companyQuit(savedCareer.getCompanyQuit())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> updateTrainerCareer(Long id, TrainerCareerRequestDto dto) {
        TrainerCareerResponseDto responseDto = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerCareer career = trainerCareerRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_CAREER));

        career.setCompanyName(dto.getCompanyName());
        career.setCompanyJoin(dto.getCompanyJoin());
        career.setCompanyQuit(dto.getCompanyQuit());

        TrainerCareer updateCareer = trainerCareerRepository.save(career);

        responseDto = TrainerCareerResponseDto.builder()
                .id(updateCareer.getId())
                .trainerId(updateCareer.getTrainerInfo().getId())
                .companyName(updateCareer.getCompanyName())
                .companyJoin(updateCareer.getCompanyJoin())
                .companyQuit(updateCareer.getCompanyQuit())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> deleteTrainerCareer(Long id, Long careerId) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerCareer career = trainerCareerRepository.findById(careerId)
                        .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_CAREER));

        trainerCareerRepository.delete(career);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);
    }

    @Override
    public ResponseDto<Void> deleteAllTrainerCareer(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        List<TrainerCareer> career = trainerCareerRepository.findByTrainerInfoId(trainer.getId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_CAREER));

        trainerCareerRepository.deleteAll(career);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);
    }

    @Override
    public ResponseDto<List<TrainerCareerResponseDto>> getCareerList(Long id) {

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        List<TrainerCareer> careers = trainerCareerRepository.findByTrainerInfoId(trainer.getId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_CAREER));

        List<TrainerCareerResponseDto> responseDto = careers.stream()
                .map(career-> TrainerCareerResponseDto.builder()
                        .id(career.getId())
                        .companyName(career.getCompanyName())
                        .companyJoin(career.getCompanyJoin())
                        .companyQuit(career.getCompanyQuit())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<TrainerRecentCareerResponseDto> getRecentCareer(Long id) {
        TrainerRecentCareerResponseDto responseDto = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerCareer career = trainerCareerRepository.findTopByTrainerInfoIdOrderByCompanyQuitDesc(trainer.getId());

        responseDto = TrainerRecentCareerResponseDto.builder()
                .companyName(career.getCompanyName())
                .companyJoin(career.getCompanyJoin())
                .companyQuit(career.getCompanyQuit())
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

}
