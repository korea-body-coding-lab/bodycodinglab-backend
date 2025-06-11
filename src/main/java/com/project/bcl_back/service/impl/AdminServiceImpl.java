package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.trainerInfo.Status;
import com.project.bcl_back.common.enums.user.UserRole;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerResponseDto;
import com.project.bcl_back.entity.Role;
import com.project.bcl_back.entity.TrainerChangeLog;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.RoleRepository;
import com.project.bcl_back.repository.TrainerChangeLogRepository;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final TrainerChangeLogRepository trainerChangeLogRepository;

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
    public ResponseDto<GetTrainerResponseDto> getTrainer(Long trainerId) {
        TrainerInfo trainer = trainerInfoRepository.findById(trainerId)
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerResDto(trainer));
    }

    @Override
    @Transactional
    public ResponseDto<GetTrainerResponseDto> updateTrainerStatus(Long id, Long trainerId, UpdateTrainerStatusRequestDto dto) {
        GetTrainerResponseDto data = null;
        String changeReason = dto.getChangeReason();

        TrainerInfo trainer = trainerInfoRepository.findById(trainerId)
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        if (trainer.getStatus().equals(dto.getNewStatus())) {
            return ResponseDto.fail(ResponseCode.ALREADY_EQUAL_STATUS, ResponseMessage.ALREADY_EQUAL_STATUS);
        }

        if (changeReason == null || changeReason.isEmpty()) {
            changeReason = dto.getNewStatus().toString();
        }

        Status prevStatus = trainer.getStatus();
        trainer.setStatus(dto.getNewStatus());
        TrainerInfo savedTrainer = trainerInfoRepository.save(trainer);

       TrainerChangeLog log = new TrainerChangeLog(id, savedTrainer, prevStatus, changeReason);
       trainerChangeLogRepository.save(log);

       data = toGetTrainerResDto(savedTrainer);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    private GetAllTrainersResponseDto toGetAllTrainersResDto(User user) {
        return GetAllTrainersResponseDto.builder()
                .userId(user.getId())
                .trainerId(user.getTrainerInfo().getId())
                .username(user.getUsername())
                .name(user.getName())
                .age(Year.now().getValue() - user.getBirthdate().getYear())
                .gender(user.getGender())
                .status(user.getTrainerInfo().getStatus())
                .createAt(DateUtils.format(user.getCreatedAt()))
                .build();
    }

    private GetTrainerResponseDto toGetTrainerResDto(TrainerInfo trainer) {
        return GetTrainerResponseDto.builder()
                .userId(trainer.getUser().getId())
                .trainerId(trainer.getId())
                .username(trainer.getUser().getUsername())
                .name(trainer.getUser().getName())
                .birthdate(trainer.getUser().getBirthdate())
                .age(Year.now().getValue() - trainer.getUser().getBirthdate().getYear())
                .gender(trainer.getUser().getGender())
                .phone(trainer.getUser().getPhone())
                .email(trainer.getUser().getEmail())
                .jobAddress(trainer.getJobAddress())
                .createdAt(DateUtils.format(trainer.getUser().getCreatedAt()))
                .status(trainer.getStatus())
                .build();
    }
}
