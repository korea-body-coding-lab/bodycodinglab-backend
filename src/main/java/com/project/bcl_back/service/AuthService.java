package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.SignInUserRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpMemberRequestDto;
import com.project.bcl_back.dto.auth.response.SignInUserResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpMemberResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {
    ResponseDto<SignUpMemberResponseDto> memberSignup(@Valid SignUpMemberRequestDto dto, MultipartFile file) throws IOException;
    ResponseDto<SignInUserResponseDto> login(@Valid SignInUserRequestDto dto);
}
