package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.SignInUserRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpMemberRequestDto;
import com.project.bcl_back.dto.auth.response.SignInUserResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpMemberResponseDto;
import com.project.bcl_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.USER_API)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";

    // 일반회원 회원가입
    @PostMapping(POST_SIGN_UP + "/member")
    public ResponseEntity<ResponseDto<SignUpMemberResponseDto>> memberSignup(@Valid @RequestBody SignUpMemberRequestDto dto) {
        ResponseDto<SignUpMemberResponseDto> response = userService.memberSignup(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 로그인
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<SignInUserResponseDto>> login(@Valid @RequestBody SignInUserRequestDto dto) {
        ResponseDto<SignInUserResponseDto> response = userService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}