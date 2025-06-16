package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.common.enums.user.UserRole;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerDetailResponseDto;
import com.project.bcl_back.entity.Role;
import com.project.bcl_back.entity.TrainerChangeLog;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.RoleRepository;
import com.project.bcl_back.repository.TrainerChangeLogRepository;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.AdminService;
import com.project.bcl_back.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final TrainerChangeLogRepository trainerChangeLogRepository;
    private final MailService mailService;

    @Override
    public ResponseDto<List<GetAllTrainersResponseDto>> getAllTrainers() {
        List<GetAllTrainersResponseDto> data = null;

        Role role = roleRepository.findByName(UserRole.TRAINER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.TRAINER).build()));

        data = userRepository.findByRole(role).stream()
                .map(this::toGetAllTrainersResDto)
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<GetTrainerDetailResponseDto> getTrainer(Long trainerId) {
        TrainerInfo trainer = trainerInfoRepository.findById(trainerId)
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerResDto(trainer));
    }

    @Override
    @Transactional
    public Mono<ResponseEntity<ResponseDto<GetTrainerDetailResponseDto>>> updateTrainerStatus(Long id, Long trainerId, UpdateTrainerStatusRequestDto dto) {
        TrainerInfo trainer = trainerInfoRepository.findById(trainerId)
                .orElse(null);

        if (trainer == null) {
            return Mono.just(ResponseDto.toResponseEntity(HttpStatus.BAD_REQUEST, ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND)));
        }

        if (trainer.getTrainerStatus().equals(dto.getNewStatus())) {
            return Mono.just(ResponseDto.toResponseEntity(HttpStatus.BAD_REQUEST, ResponseDto.fail(ResponseCode.ALREADY_EQUAL_STATUS, ResponseMessage.ALREADY_EQUAL_STATUS)));
        }

        TrainerStatus prevStatus = trainer.getTrainerStatus();

        trainer.setTrainerStatus(dto.getNewStatus());
        TrainerInfo savedTrainer = trainerInfoRepository.save(trainer);

        createLog(id, savedTrainer,prevStatus,dto.getChangeReason());

        SendTrainerApprovalResultEmailRequestDto sendEmailDto = new SendTrainerApprovalResultEmailRequestDto(savedTrainer.getUser().getEmail(), dto.getNewStatus(), dto.getChangeReason());

        return mailService.sendTrainerApprovalResultEmail(sendEmailDto)
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return Mono.just(ResponseDto.toResponseEntity(HttpStatus.OK,
                                ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerResDto(savedTrainer))));
                    } else {
                        return Mono.just(ResponseDto.toResponseEntity(HttpStatus.BAD_REQUEST,
                                ResponseDto.fail(ResponseCode.MAIL_SEND_FAIL, ResponseMessage.MAIL_SEND_FAIL)));
                    }
                });
    }

    private GetAllTrainersResponseDto toGetAllTrainersResDto(User user) {
        return GetAllTrainersResponseDto.builder()
                .id(user.getId())
                .trainerId(user.getTrainerInfo().getId())
                .username(user.getUsername())
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .jobAddress(user.getTrainerInfo().getJobAddress())
                .createdAt(DateUtils.format(user.getCreatedAt()))
                .status(user.getTrainerInfo().getTrainerStatus())
                .build();
    }

    private GetTrainerDetailResponseDto toGetTrainerResDto(TrainerInfo trainer) {
        return GetTrainerDetailResponseDto.builder()
                .userId(trainer.getUser().getId())
                .trainerId(trainer.getId())
                .username(trainer.getUser().getUsername())
                .name(trainer.getUser().getName())
                .birthdate(trainer.getUser().getBirthdate())
                .gender(trainer.getUser().getGender())
                .phone(trainer.getUser().getPhone())
                .email(trainer.getUser().getEmail())
                .jobAddress(trainer.getJobAddress())
                .createdAt(DateUtils.format(trainer.getUser().getCreatedAt()))
                .status(trainer.getTrainerStatus())
                .build();
    }

    private void createLog(Long id, TrainerInfo savedTrainer, TrainerStatus prevStatus, String changeReason) {
        TrainerChangeLog log = new TrainerChangeLog(id, savedTrainer, prevStatus, changeReason);
        trainerChangeLogRepository.save(log);
    }
}
