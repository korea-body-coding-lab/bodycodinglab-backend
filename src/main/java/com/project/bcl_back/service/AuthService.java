package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.SignInUserRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpMemberRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpTrainerRequestDto;
import com.project.bcl_back.dto.auth.response.SignInUserResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpMemberResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpTrainerResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {
    ResponseDto<SignUpMemberResponseDto> memberSignup(@Valid SignUpMemberRequestDto dto, MultipartFile profile) throws IOException;
    ResponseDto<SignUpTrainerResponseDto> trainerSignup(@Valid SignUpTrainerRequestDto dto, MultipartFile attachmentFile, MultipartFile profile) throws IOException;
    ResponseDto<SignInUserResponseDto> login(@Valid SignInUserRequestDto dto);
}
