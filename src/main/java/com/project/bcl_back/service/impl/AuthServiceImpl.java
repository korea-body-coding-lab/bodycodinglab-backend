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
import com.project.bcl_back.service.UploadFileService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UploadFileService uploadFileService;

    @Value("${jwt.expiration}")
    private String jwtExpirationMs;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private AuthenticationManager authenticationManager;

    @Lazy
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    

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
                .status(com.project.bcl_back.common.enums.trainerInfo.Status.NOT_APPROVE)
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

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        User user = (User) auth.getPrincipal();

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();

        data = SignInUserResponseDto.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .profileImage(uploadFileService.getProfileImage(user.getId(), TargetType.PROFILE))
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}