package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.common.enums.member.Status;
import com.project.bcl_back.common.enums.user.UserRole;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.SignInUserRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpMemberRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpTrainerRequestDto;
import com.project.bcl_back.dto.auth.response.SignInUserResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpMemberResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpTrainerResponseDto;
import com.project.bcl_back.entity.*;
import com.project.bcl_back.repository.*;
import com.project.bcl_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UploadFileRepository uploadFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ResponseDto<SignUpMemberResponseDto> memberSignup(SignUpMemberRequestDto dto, MultipartFile file) throws IOException {
        SignUpMemberResponseDto data = null;

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessage.DUPLICATED_USER_ID);
        }

        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessage.DUPLICATED_TEL_NUMBER);
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessage.NOT_MATCH_PASSWORD);
        }

        Role userRole = roleRepository.findByName(UserRole.MEMBER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.MEMBER).build()));

        User user = User.builder()
                .role(userRole)
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
        userRepository.save(user);

        Member member = Member.builder()
                .user(user)
                .memberAddress(dto.getAddress())
                .status(Status.NOT_PAYMENT)
                .build();
        memberRepository.save(member);

        if (file != null && !file.isEmpty()) {
            user.setProfileImage(saveFile(file, user.getId(), TargetType.PROFILE));
            userRepository.save(user);
        }

        data = SignUpMemberResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<SignUpTrainerResponseDto> trainerSignup(SignUpTrainerRequestDto dto, MultipartFile attachmentFile, MultipartFile profile) throws IOException {
        SignUpTrainerResponseDto data = null;

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessage.DUPLICATED_USER_ID);
        }

        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessage.DUPLICATED_TEL_NUMBER);
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessage.NOT_MATCH_PASSWORD);
        }

        if (attachmentFile == null || attachmentFile.isEmpty()) {
            return ResponseDto.fail(ResponseCode.REQUIRED_FIELD_MISSING, ResponseMessage.REQUIRED_FIELD_MISSING + "(첨부파일)");
        }

        Role userRole = roleRepository.findByName(UserRole.TRAINER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.TRAINER).build()));

        User user = User.builder()
                .role(userRole)
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
        userRepository.save(user);

        if (profile != null && !profile.isEmpty()) {
            user.setProfileImage(saveFile(profile, user.getId(), TargetType.PROFILE));
            userRepository.save(user);
        }

        TrainerInfo trainerInfo = TrainerInfo.builder()
                .user(user)
                .jobAddress(dto.getJobAddress())
                .status(com.project.bcl_back.common.enums.trainerInfo.Status.NOT_APPROVE)
                .build();
        trainerInfoRepository.save(trainerInfo);

        trainerInfo.setAttachmentFile(saveFile(attachmentFile, trainerInfo.getId(), TargetType.ATTACHMENT));
        trainerInfoRepository.save(trainerInfo);

        data = SignUpTrainerResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<SignInUserResponseDto> login(SignInUserRequestDto dto) {
        return null;
    }

    private UploadFile saveFile(MultipartFile file, Long targetId, TargetType targetType) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename();
        String uuidName = UUID.randomUUID() + "_" + original;
        String fullPath = uploadDir + "/" + uuidName;
        file.transferTo(new File(fullPath));

        UploadFile uf = UploadFile.builder()
                .originalName(original)
                .fileName(uuidName)
                .filePath("/files/" + uuidName)
                .fileSize(file.getSize())
                .fileType(file.getContentType())
                .targetId(targetId)
                .targetType(targetType)
                .build();
        uploadFileRepository.save(uf);

        return uf;
    }
}