package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.SignInUserRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpMemberRequestDto;
import com.project.bcl_back.dto.auth.response.SignInUserResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpMemberResponseDto;
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
    public ResponseEntity<ResponseDto<SignUpMemberResponseDto>> memberSignup(@Valid @RequestBody SignUpMemberRequestDto dto, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ResponseDto<SignUpMemberResponseDto> response = authService.memberSignup(dto, file);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 로그인
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<SignInUserResponseDto>> login(@Valid @RequestBody SignInUserRequestDto dto) {
        ResponseDto<SignInUserResponseDto> response = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}