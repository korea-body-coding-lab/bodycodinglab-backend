package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.request.UserSignInRequestDto;
import com.project.bcl_back.dto.user.request.UserSignUpRequestDto;
import com.project.bcl_back.dto.user.response.UserSignInResponseDto;
import com.project.bcl_back.dto.user.response.UserSignUpResponseDto;
import com.project.bcl_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        return null;
    }
}