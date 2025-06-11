package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.common.enums.member.MemberStatus;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.common.enums.user.UserRole;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.SignInUserRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpMemberRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpTrainerRequestDto;
import com.project.bcl_back.dto.auth.response.SignInUserResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpMemberResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpTrainerResponseDto;
import com.project.bcl_back.entity.*;
import com.project.bcl_back.provider.JwtProvider;
import com.project.bcl_back.repository.*;
import com.project.bcl_back.service.AuthService;
import com.project.bcl_back.service.UploadFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UploadFileService uploadFileService;
    private final JwtProvider jwtProvider;

    @Transactional
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
                .status(MemberStatus.NOT_PAYMENT)
                .build();
        memberRepository.save(member);

        if (file != null && !file.isEmpty()) {
            user.setProfileImage(uploadFileService.saveFile(file, user.getId(), TargetType.PROFILE));
            userRepository.save(user);
        }

        data = SignUpMemberResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Transactional
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
            user.setProfileImage(uploadFileService.saveFile(profile, user.getId(), TargetType.PROFILE));
            userRepository.save(user);
        }

        TrainerInfo trainerInfo = TrainerInfo.builder()
                .user(user)
                .jobAddress(dto.getJobAddress())
                .trainerStatus(TrainerStatus.PENDING)
                .build();
        trainerInfoRepository.save(trainerInfo);

        trainerInfo.setAttachmentFile(uploadFileService.saveFile(attachmentFile, trainerInfo.getId(), TargetType.ATTACHMENT));
        trainerInfoRepository.save(trainerInfo);

        data = SignUpTrainerResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<SignInUserResponseDto> login(SignInUserRequestDto dto) throws IOException {
        SignInUserResponseDto data = null;
        User user = null;

        user = userRepository.findByUsername(dto.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.NO_EXIST_USER_ID, ResponseMessage.NO_EXIST_USER_ID);
        }

        if (!checkPassword(user, dto.getPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_CORRECT_PASSWORD, ResponseMessage.NOT_CORRECT_PASSWORD);
        }

        String token = jwtProvider.generateJwtToken(user.getId(), user.getRole().getName().toString());

        data = SignInUserResponseDto.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
//                .profileImage(uploadFileService.getProfileImage(user.getId(), TargetType.PROFILE))
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}