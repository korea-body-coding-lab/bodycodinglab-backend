package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.SignInUserRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpMemberRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpTrainerRequestDto;
import com.project.bcl_back.dto.auth.response.SignInUserResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpMemberResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpTrainerResponseDto;
import com.project.bcl_back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";

    // 일반회원 회원가입
    @PostMapping(POST_SIGN_UP + "/member")
    public ResponseEntity<ResponseDto<SignUpMemberResponseDto>> memberSignup(
            @Valid @RequestPart(value = "data") SignUpMemberRequestDto dto,
            @RequestPart(value = "profile", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<SignUpMemberResponseDto> response = authService.memberSignup(dto, file);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 트레이너 회원가입
    @PostMapping(POST_SIGN_UP + "/trainer")
    public ResponseEntity<ResponseDto<SignUpTrainerResponseDto>> trainerSignup(
            @Valid @RequestPart(value = "data") SignUpTrainerRequestDto dto,
            @RequestPart(value = "attachmentFile") MultipartFile attachmentFile,
            @RequestPart(value = "profile", required = false) MultipartFile profile
    ) throws IOException {
        ResponseDto<SignUpTrainerResponseDto> response = authService.trainerSignup(dto, attachmentFile, profile);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 로그인
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<SignInUserResponseDto>> login(@Valid @RequestBody SignInUserRequestDto dto) throws IOException {
        ResponseDto<SignInUserResponseDto> response = authService.login(dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}