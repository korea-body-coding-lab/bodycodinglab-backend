package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.request.DeleteUserRequestDto;
import com.project.bcl_back.dto.user.request.UpdateMemberInfoRequestDto;
import com.project.bcl_back.dto.user.request.UpdateTrainerInfoRequestDto;
import com.project.bcl_back.dto.user.response.DeleteUserResponseDto;
import com.project.bcl_back.dto.user.response.GetMemberInfoResponseDto;
import com.project.bcl_back.dto.user.response.GetTrainerInfoResponseDto;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.AuthService;
import com.project.bcl_back.service.UploadFileService;
import com.project.bcl_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UploadFileService uploadFileService;
    private final AuthService authService;

    @Override
    public ResponseDto<GetMemberInfoResponseDto> getMemberInfo(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.AUTHORIZATION_FAIL, ResponseMessage.AUTHORIZATION_FAIL);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetMemberInfoResponseDto(user));
    }

    @Override
    @Transactional
    public ResponseDto<GetMemberInfoResponseDto> updateMemberInfo(Long id, UpdateMemberInfoRequestDto dto, MultipartFile profile) throws IOException {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.AUTHORIZATION_FAIL, ResponseMessage.AUTHORIZATION_FAIL);
        }

        user.setName(dto.getName());
//        user.getMember().setMemberAddress(dto.getMemberAddress());
        userRepository.save(user);

        if (profile != null && !profile.isEmpty()) {
            user.setProfileImage(uploadFileService.saveFile(profile, user.getId(), TargetType.PROFILE));
            userRepository.save(user);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetMemberInfoResponseDto(user));
    }

    @Override
    public ResponseDto<GetTrainerInfoResponseDto> getTrainerInfo(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.AUTHORIZATION_FAIL, ResponseMessage.AUTHORIZATION_FAIL);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerInfoResponseDto(user));
    }

    @Override
    @Transactional
    public ResponseDto<GetTrainerInfoResponseDto> updateTrainerInfo(Long id, UpdateTrainerInfoRequestDto dto, MultipartFile profile) throws IOException {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.AUTHORIZATION_FAIL, ResponseMessage.AUTHORIZATION_FAIL);
        }

        user.setName(dto.getName());
        userRepository.save(user);

        if (profile != null && !profile.isEmpty()) {
            user.setProfileImage(uploadFileService.saveFile(profile, user.getId(), TargetType.PROFILE));
            userRepository.save(user);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerInfoResponseDto(user));
    }

    @Override
    public ResponseDto<DeleteUserResponseDto> deleteUser(Long id, @Valid DeleteUserRequestDto dto) {
        DeleteUserResponseDto data = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.AUTHORIZATION_FAIL, ResponseMessage.AUTHORIZATION_FAIL);
        }

        if (!dto.getDeleteMessage().equals("탈퇴하겠습니다.")) {
            return ResponseDto.fail(ResponseCode.INVALID_INPUT, ResponseMessage.INVALID_INPUT);
        }

        if (!authService.checkPassword(user, dto.getPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_CORRECT_PASSWORD, ResponseMessage.NOT_CORRECT_PASSWORD);
        }

        userRepository.delete(user);

        data = DeleteUserResponseDto.builder()
                .name(user.getName())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    private GetMemberInfoResponseDto toGetMemberInfoResponseDto(User user) {
        return GetMemberInfoResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .phone(user.getPhone())
                .email(user.getEmail())
//                .memberAddress(user.getMember().getMemberAddress())
                .build();
    }

    private GetTrainerInfoResponseDto toGetTrainerInfoResponseDto(User user) {
        return GetTrainerInfoResponseDto.builder()
                .trainerId(user.getTrainerInfo().getId())
                .username(user.getUsername())
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .phone(user.getPhone())
                .email(user.getEmail())
                .jobAddress(user.getTrainerInfo().getJobAddress())
                .trainerStatus(user.getTrainerInfo().getTrainerStatus())
                .build();
    }
}
