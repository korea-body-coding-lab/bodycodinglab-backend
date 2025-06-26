package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.*;
import com.project.bcl_back.dto.auth.response.*;
import com.project.bcl_back.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {
    ResponseDto<SignUpMemberResponseDto> signupMember(@Valid SignUpMemberRequestDto dto, MultipartFile profile) throws IOException;
    ResponseDto<SignUpTrainerResponseDto> signupTrainer(@Valid SignUpTrainerRequestDto dto, MultipartFile attachmentFile, MultipartFile profile) throws IOException;
    ResponseDto<? extends LoginUserResponseDto> login(@Valid LoginUserRequestDto dto) throws IOException;
    ResponseDto<RecoverUsernameResponseDto> recoverUsername(@Valid RecoverUsernameRequestDto dto);
    ResponseDto<GetResetPasswordUserResponseDto> getResetPasswordUser(@Valid GetResetPasswordUserRequestDto dto);
    ResponseDto<String> resetPassword(String token, @Valid ResetPasswordRequestDto dto);
    boolean checkPassword(User user, String password);
    boolean checkEmail(String email);
}
