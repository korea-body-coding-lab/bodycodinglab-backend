package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.common.enums.member.MemberStatus;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.common.enums.user.UserRole;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.*;
import com.project.bcl_back.dto.auth.response.*;
import com.project.bcl_back.entity.*;
import com.project.bcl_back.provider.JwtProvider;
import com.project.bcl_back.repository.*;
import com.project.bcl_back.service.AuthService;
import com.project.bcl_back.service.UploadFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

    @Override
    @Transactional
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

    @Override
    @Transactional
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
        User user = userRepository.findByUsername(dto.getUsername())
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
    public ResponseDto<FindUserIdResponseDto> findUserId(FindUserIdRequestDto dto) {
        FindUserIdResponseDto data = null;

        User user = userRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.NO_EXIST_EMAIL, ResponseMessage.NO_EXIST_EMAIL);
        }

        if (!user.getName().equals(dto.getName()) || !user.getBirthdate().equals(dto.getBirthdate())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_INFORMATION, ResponseMessage.NOT_MATCH_INFORMATION);
        }

        data = new FindUserIdResponseDto(user.getUsername());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<UserInformationToResetPasswordResponseDto> findUserToResetPassword(GetUserInformationToResetPasswordRequestDto dto) {
        UserInformationToResetPasswordResponseDto data = null;
        User user = userRepository.findByUsername(dto.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        if (!user.getName().equals(dto.getName()) || !user.getBirthdate().equals(dto.getBirthdate()) || !user.getEmail().equals(dto.getEmail())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_INFORMATION, ResponseMessage.NOT_MATCH_INFORMATION);
        }

        data = new UserInformationToResetPasswordResponseDto(user.getId());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public Mono<ResponseEntity<String>> resetPassword(String email, ResetPasswordRequestDto dto) {
        if (email == null) {
            return Mono.just(ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseMessage.MAIL_AUTH_FAIL));
        }

        return Mono.fromCallable(() -> {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NO_EXIST_EMAIL));

            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ResponseMessage.NOT_MATCH_PASSWORD);
            }

            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(user);

            return ResponseEntity.ok(ResponseCode.SUCCESS + " (비밀번호 재설정)");
        }).onErrorResume(e -> Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.RESET_PASSWORD_FAIL))
        ).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public ResponseDto<Void> reapplyTrainer(String email, ReapplyTrainerRequestDto dto, MultipartFile attachmentFile) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        if (!user.getRole().getName().equals(UserRole.TRAINER)) {
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        user.getTrainerInfo().setJobAddress(dto.getJobAddress());
        // 이자리에 파일 업데이트 소스 구현 예정
        user.getTrainerInfo().setTrainerStatus(TrainerStatus.PENDING);
        userRepository.save(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}