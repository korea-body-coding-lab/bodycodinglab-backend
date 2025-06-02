package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.request.UserSignInRequestDto;
import com.project.bcl_back.dto.user.request.UserSignUpRequestDto;
import com.project.bcl_back.dto.user.response.UserSignInResponseDto;
import com.project.bcl_back.dto.user.response.UserSignUpResponseDto;
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

    // 1) 일반회원 회원가입
    @PostMapping(POST_SIGN_UP + "/member")
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signup(@Valid @RequestBody UserSignUpRequestDto dto) {
        ResponseDto<UserSignUpResponseDto> response = userService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 로그인
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<UserSignInResponseDto>> login(@Valid @RequestBody UserSignInRequestDto dto) {
        ResponseDto<UserSignInResponseDto> response = userService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}