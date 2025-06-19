package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.trainer.request.TrainerInfoRequestDto;
import com.project.bcl_back.dto.trainer.response.TrainerCareerResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerLicenseResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerListResponseDto;
import com.project.bcl_back.dto.trainer.response.TrainerInfoResponseDto;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.UploadFileRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.TrainerInfoService;
import com.project.bcl_back.service.UploadFileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerInfoServiceImpl implements TrainerInfoService {
    private final TrainerInfoRepository trainerInfoRepository;
    private final UserRepository userRepository;
    private final UploadFileRepository uploadFileRepository;
    private final UploadFileService uploadFileService;

    @Override
    public ResponseDto<TrainerInfoResponseDto> updateTrainerInfo(Long id, TrainerInfoRequestDto dto, MultipartFile file) throws IOException {
        TrainerInfoResponseDto responseDto = null;

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

        System.out.println(trainer.getId());
        TrainerInfo info = trainerInfoRepository.findById(trainer.getId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.TRAINER_NOT_FOUND));

        String formattedEntrance = DateUtils.yearDateFormat(DateUtils.yearDateParse(dto.getEducationEntrance()));
        String formattedGraduate = DateUtils.yearDateFormat(DateUtils.yearDateParse(dto.getEducationGraduate()));

        info.setJobAddress(dto.getJobAddress());
        info.setShortIntroduce(dto.getShortIntroduce());
        info.setLongIntroduce(dto.getLongIntroduce());
        info.setEducationName(dto.getEducationName());
        info.setEducationEntrance(formattedEntrance);
        info.setEducationGraduate(formattedGraduate);

        TrainerInfo updateInfo = trainerInfoRepository.save(info);

        if (file != null && !file.isEmpty()) {
            uploadFileService.saveFile(file, user.getTrainerInfo().getId(), TargetType.INFOS);
            trainerInfoRepository.save(info);
        }

        responseDto = TrainerInfoResponseDto.builder()
                .id(updateInfo.getId())
                .jobAddress(updateInfo.getJobAddress())
                .shortIntroduce(updateInfo.getShortIntroduce())
                .longIntroduce(updateInfo.getLongIntroduce())
                .educationName(updateInfo.getEducationName())
                .educationEntrance(updateInfo.getEducationEntrance())
                .educationGraduate(updateInfo.getEducationGraduate())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }
}