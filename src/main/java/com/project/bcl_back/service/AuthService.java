package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.*;
import com.project.bcl_back.dto.auth.response.*;
import com.project.bcl_back.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface AuthService {
    ResponseDto<SignUpMemberResponseDto> memberSignup(@Valid SignUpMemberRequestDto dto, MultipartFile profile) throws IOException;
    ResponseDto<SignUpTrainerResponseDto> trainerSignup(@Valid SignUpTrainerRequestDto dto, MultipartFile attachmentFile, MultipartFile profile) throws IOException;
    ResponseDto<SignInUserResponseDto> login(@Valid SignInUserRequestDto dto) throws IOException;
    ResponseDto<FindUserIdResponseDto> findUserId(@Valid FindUserIdRequestDto dto);
    ResponseDto<UserInformationToResetPasswordResponseDto> findUserToResetPassword(@Valid GetUserInformationToResetPasswordRequestDto dto);
    Mono<ResponseEntity<String>> resetPassword(String email, @Valid ResetPasswordRequestDto dto);
    ResponseDto<Void> reapplyTrainer(String email, @Valid ReapplyTrainerRequestDto dto, MultipartFile attachmentFile);
    boolean checkPassword(User user, String password);
    boolean checkEmail(String email);
}
