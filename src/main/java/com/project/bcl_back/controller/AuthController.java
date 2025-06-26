package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.*;
import com.project.bcl_back.dto.auth.response.*;
import com.project.bcl_back.service.AuthService;
import com.project.bcl_back.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MailService mailService;

    private static final String SIGNUP_MEMBER = "/signup/member";
    private static final String SIGNUP_TRAINER = "/signup/trainer";
    private static final String LOGIN = "/login";
    private static final String USERNAME_RECOVERY = "/username/recovery";
    private static final String PASSWORD_RESET_USER = "/password/reset-user";
    private static final String PASSWORD_RESET = "/password/reset";
    private static final String PASSWORD_RESET_EMAIL = "/password/reset-email";
    private static final String EMAIL_VERIFY = "/email/verify";


    @PostMapping(SIGNUP_MEMBER)
    public ResponseEntity<ResponseDto<SignUpMemberResponseDto>> signupMember(
            @Valid @RequestPart(value = "dto") SignUpMemberRequestDto dto,
            @RequestPart(value = "profile", required = false) MultipartFile profile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, authService.signupMember(dto, profile));
    }

    @PostMapping(SIGNUP_TRAINER)
    public ResponseEntity<ResponseDto<SignUpTrainerResponseDto>> signupTrainer(
            @Valid @RequestPart(value = "dto") SignUpTrainerRequestDto dto,
            @RequestPart(value = "attachmentFile") MultipartFile attachmentFile,
            @RequestPart(value = "profile", required = false) MultipartFile profile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, authService.signupTrainer(dto, attachmentFile, profile));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<ResponseDto<? extends LoginUserResponseDto>> login(@Valid @RequestBody LoginUserRequestDto dto) throws IOException {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(USERNAME_RECOVERY)
    public ResponseEntity<ResponseDto<RecoverUsernameResponseDto>> recoverUsername(@Valid @RequestBody RecoverUsernameRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.recoverUsername(dto));
    }

    @PostMapping(PASSWORD_RESET_USER)
    public ResponseEntity<ResponseDto<GetResetPasswordUserResponseDto>> getResetPasswordUser(@Valid @RequestBody GetResetPasswordUserRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.getResetPasswordUser(dto));
    }

    @PostMapping(PASSWORD_RESET)
    public ResponseEntity<ResponseDto<String>> resetPassword(@RequestParam String token, @Valid @RequestBody ResetPasswordRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.resetPassword(token, dto));
    }

    @PostMapping(PASSWORD_RESET_EMAIL)
    public Mono<ResponseEntity<ResponseDto<String>>> sendResetPasswordEmail(@Valid @RequestBody SendEmailRequestDto dto) {
        return mailService.sendResetPasswordEmail(dto);
    }

    @GetMapping(EMAIL_VERIFY)
    public Mono<ResponseEntity<ResponseDto<String>>> verifyEmail(@RequestParam String token) {
        return mailService.verifyEmail(token);
    }

}