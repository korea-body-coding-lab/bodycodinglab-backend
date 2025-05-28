package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.UserSignInRequestDto;
import com.project.bcl_back.dto.auth.request.UserSignUpRequestDto;
import com.project.bcl_back.dto.auth.response.UserSignInResponseDto;
import com.project.bcl_back.dto.auth.response.UserSignUpResponseDto;
import com.project.bcl_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        return null;
    }
}