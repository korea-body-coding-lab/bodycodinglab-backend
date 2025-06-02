package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.request.UserSignInRequestDto;
import com.project.bcl_back.dto.user.request.UserSignUpRequestDto;
import com.project.bcl_back.dto.user.response.UserSignInResponseDto;
import com.project.bcl_back.dto.user.response.UserSignUpResponseDto;
import jakarta.validation.Valid;

public interface UserService {
    ResponseDto<UserSignUpResponseDto> signup(@Valid UserSignUpRequestDto dto);
    ResponseDto<UserSignInResponseDto> login(@Valid UserSignInRequestDto dto);
}
