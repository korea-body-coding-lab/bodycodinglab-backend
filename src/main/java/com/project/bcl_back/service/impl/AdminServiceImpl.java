package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerDetailResponseDto;
import com.project.bcl_back.entity.*;
import com.project.bcl_back.repository.*;
import com.project.bcl_back.service.AdminService;
import com.project.bcl_back.service.MailService;
import com.project.bcl_back.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final TrainerInfoRepository trainerInfoRepository;
    private final TrainerChangeLogRepository trainerChangeLogRepository;
    private final TrainerListViewRepository trainerListViewRepository;
    private final MailService mailService;
    private final UploadFileService uploadFileService;

    @Override
    public ResponseDto<Page<GetAllTrainersResponseDto>> getAllTrainers(int page, int size, TrainerStatus status) {
        Page<GetAllTrainersResponseDto> data = null;
        Pageable pageable = PageRequest.of(page, size, Sort.by("trainerId").descending());
        Page<TrainerListView> trainerPage = null;

        if (status == null) {
            trainerPage = trainerListViewRepository.findAll(pageable);
        } else {
            trainerPage = trainerListViewRepository.findByStatus(status, pageable);
        }

        data = trainerPage.map(this::toGetAllTrainersResDto);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<GetTrainerDetailResponseDto> getTrainer(Long trainerId) {
        TrainerInfo trainer = trainerInfoRepository.findById(trainerId)
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        String attachmentFileUrl = null;
        UploadFile attachment = uploadFileService.findByTargetIdAndTargetType(trainer.getId(), TargetType.ATTACHMENT);
        if (attachment != null) {
            attachmentFileUrl = ApiMappingPattern.FILE_API + "/trainer-attachment/" + trainer.getId() + "/" + trainer.getAttachmentFile().getTargetType();
        }

        String profileImageUrl = null;
        UploadFile profileImage = uploadFileService.findByTargetIdAndTargetType(trainer.getUser().getId(), TargetType.PROFILE);
        if (profileImage != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/profile/" + trainer.getUser().getId() + "/" + TargetType.PROFILE;
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerResDto(trainer, attachmentFileUrl, profileImageUrl));
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

        String attachmentFileUrl;
        UploadFile attachment = uploadFileService.findByTargetIdAndTargetType(trainer.getId(), TargetType.ATTACHMENT);
        if (attachment != null) {
            attachmentFileUrl = ApiMappingPattern.FILE_API + "/trainer-attachment/" + trainer.getId() + "/" + trainer.getAttachmentFile().getTargetType();
        } else {
            attachmentFileUrl = null;
        }

        String profileImageUrl;
        UploadFile profileImage = uploadFileService.findByTargetIdAndTargetType(trainer.getUser().getId(), TargetType.PROFILE);
        if (profileImage != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/profile/" + trainer.getUser().getId() + "/" + TargetType.PROFILE;
        } else {
            profileImageUrl = null;
        }

        TrainerStatus prevStatus = trainer.getTrainerStatus();

        trainer.setTrainerStatus(dto.getNewStatus());
        TrainerInfo savedTrainer = trainerInfoRepository.save(trainer);

        createLog(id, savedTrainer,prevStatus,dto.getChangeReason());

        SendTrainerApprovalResultEmailRequestDto sendEmailDto = new SendTrainerApprovalResultEmailRequestDto(savedTrainer.getUser().getEmail(), dto.getNewStatus(), dto.getChangeReason());

        return mailService.sendTrainerApprovalResultEmail(sendEmailDto)
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println("여기까지 왔드아아아!!");
                        return Mono.just(ResponseDto.toResponseEntity(HttpStatus.OK,
                                ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerResDto(savedTrainer, attachmentFileUrl, profileImageUrl))));
                    } else {
                        return Mono.just(ResponseDto.toResponseEntity(HttpStatus.BAD_REQUEST,
                                ResponseDto.fail(ResponseCode.MAIL_SEND_FAIL, ResponseMessage.MAIL_SEND_FAIL)));
                    }
                });
    }

    private GetAllTrainersResponseDto toGetAllTrainersResDto(TrainerListView view) {
        return GetAllTrainersResponseDto.builder()
                .trainerId(view.getTrainerId())
                .username(view.getUsername())
                .name(view.getName())
                .birthdate(view.getBirthdate())
                .jobAddress(view.getJobAddress())
                .createdAt(DateUtils.format(view.getCreatedAt()))
                .status(view.getStatus())
                .build();
    }

    private GetTrainerDetailResponseDto toGetTrainerResDto(TrainerInfo trainer, String attachmentFileUrl, String profileImageUrl) {
        return GetTrainerDetailResponseDto.builder()
                .trainerId(trainer.getId())
                .username(trainer.getUser().getUsername())
                .name(trainer.getUser().getName())
                .birthdate(trainer.getUser().getBirthdate())
                .gender(trainer.getUser().getGender())
                .phone(trainer.getUser().getPhone())
                .email(trainer.getUser().getEmail())
                .jobAddress(trainer.getJobAddress())
                .attachmentFileUrl(attachmentFileUrl)
                .createdAt(DateUtils.format(trainer.getUser().getCreatedAt()))
                .status(trainer.getTrainerStatus())
                .profileImageUrl(profileImageUrl)
                .build();
    }

    private void createLog(Long id, TrainerInfo savedTrainer, TrainerStatus prevStatus, String changeReason) {
        TrainerChangeLog log = new TrainerChangeLog(id, savedTrainer, prevStatus, changeReason);
        trainerChangeLogRepository.save(log);
    }
}
