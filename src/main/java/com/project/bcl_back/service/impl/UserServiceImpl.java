package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ApiMappingPattern;
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
import com.project.bcl_back.dto.user.response.GetUserInfoResponseDto;
import com.project.bcl_back.entity.UploadFile;
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
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetMemberInfoResponseDto(user));
    }

    @Override
    @Transactional
    public ResponseDto<GetMemberInfoResponseDto> updateMemberInfo(Long id, UpdateMemberInfoRequestDto dto) throws IOException {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            user.setName(dto.getName());
        }

        if (dto.getMemberAddress() != null && !dto.getMemberAddress().isEmpty()) {
            user.getMember().setMemberAddress(dto.getMemberAddress());
        }
        userRepository.save(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetMemberInfoResponseDto(user));
    }

    @Override
    public ResponseDto<GetTrainerInfoResponseDto> getTrainerInfo(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        String attachmentFileUrl = null;
        UploadFile attachment = uploadFileService.findByTargetIdAndTargetType(user.getTrainerInfo().getId(), TargetType.ATTACHMENT);
        if (attachment != null) {
            attachmentFileUrl = ApiMappingPattern.FILE_API + "/trainer-attachment/" + user.getTrainerInfo().getId() + "/" + user.getTrainerInfo().getAttachmentFile().getTargetType();
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerInfoResponseDto(user, attachmentFileUrl));
    }

    @Override
    @Transactional
    public ResponseDto<GetTrainerInfoResponseDto> updateTrainerInfo(Long id, UpdateTrainerInfoRequestDto dto) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        user.setName(dto.getName());
        userRepository.save(user);

        String attachmentFileUrl = null;
        UploadFile attachment = uploadFileService.findByTargetIdAndTargetType(user.getTrainerInfo().getId(), TargetType.ATTACHMENT);
        if (attachment != null) {
            attachmentFileUrl = ApiMappingPattern.FILE_API + "/trainer-attachment/" + user.getTrainerInfo().getId() + "/" + user.getTrainerInfo().getAttachmentFile().getTargetType();
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetTrainerInfoResponseDto(user, attachmentFileUrl));
    }

    @Override
    public ResponseDto<DeleteUserResponseDto> deleteUser(Long id, @Valid DeleteUserRequestDto dto) {
        DeleteUserResponseDto data = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
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

    @Override
    public ResponseDto<Void> updateProfileImage(Long id, MultipartFile profile) throws IOException {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        if (profile == null || profile.isEmpty()) {
            return ResponseDto.fail(ResponseCode.FILE_NOT_ATTACHED, ResponseMessage.FILE_NOT_ATTACHED);
        }

        user.setProfileImage(uploadFileService.updateFile(user.getId(), TargetType.PROFILE, profile));

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<Void> deleteProfileImage(Long id) throws IOException {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        uploadFileService.deleteFile(user.getId(), TargetType.PROFILE);
        user.setProfileImage(null);
        userRepository.save(user);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<GetUserInfoResponseDto> getUserInformation(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        String profileImageUrl = null;
        UploadFile profileImage = uploadFileService.findByTargetIdAndTargetType(user.getId(), TargetType.PROFILE);

        if (profileImage != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/profile/" + user.getId() + "/" + TargetType.PROFILE;
        }
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toGetUserInfoResponseDto(user, profileImageUrl));
    }

    private GetMemberInfoResponseDto toGetMemberInfoResponseDto(User user) {
        return GetMemberInfoResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .phone(user.getPhone())
                .email(user.getEmail())
                .memberAddress(user.getMember().getMemberAddress())
                .build();
    }

    private GetTrainerInfoResponseDto toGetTrainerInfoResponseDto(User user, String attachmentFileUrl) {
        return GetTrainerInfoResponseDto.builder()
                .trainerId(user.getTrainerInfo().getId())
                .username(user.getUsername())
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .phone(user.getPhone())
                .email(user.getEmail())
                .jobAddress(user.getTrainerInfo().getJobAddress())
                .attachmentFileUrl(attachmentFileUrl)
                .status(user.getTrainerInfo().getTrainerStatus())
                .build();
    }

    private GetUserInfoResponseDto toGetUserInfoResponseDto(User user, String profileImageUrl) {
        return GetUserInfoResponseDto.builder()
                .id(user.getId())
                .role(user.getRole().getName().toString())
                .username(user.getUsername())
                .name(user.getName())
                .profileImageUrl(profileImageUrl)
                .build();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
