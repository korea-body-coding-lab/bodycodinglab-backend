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

    private static final String SIGN_UP = "/sign-up";
    private static final String LOGIN = "/login";
    private static final String FIND_USERNAME = "/finding-id";
    private static final String SEND_EMAIL = "/send-reset-password-email";
    private static final String VERIFY_EMAIL = "/verify";
    private static final String RESET_PASSWORD = "/reset-password";



    @PostMapping(SIGN_UP + "/member")
    public ResponseEntity<ResponseDto<SignUpMemberResponseDto>> memberSignup(
            @Valid @RequestPart(value = "dto") SignUpMemberRequestDto dto,
            @RequestPart(value = "profile", required = false) MultipartFile profile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, authService.memberSignup(dto, profile));
    }

    @PostMapping(SIGN_UP + "/trainer")
    public ResponseEntity<ResponseDto<SignUpTrainerResponseDto>> trainerSignup(
            @Valid @RequestPart(value = "dto") SignUpTrainerRequestDto dto,
            @RequestPart(value = "attachmentFile") MultipartFile attachmentFile,
            @RequestPart(value = "profile", required = false) MultipartFile profile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, authService.trainerSignup(dto, attachmentFile, profile));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<ResponseDto<? extends LoginUserResponseDto>> login(@Valid @RequestBody LoginUserRequestDto dto) throws IOException {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(FIND_USERNAME)
    public ResponseEntity<ResponseDto<FindUsernameResponseDto>> findUserId(@Valid @RequestBody FindUsernameRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.findUserId(dto));
    }

    @PostMapping(SEND_EMAIL)
    public Mono<ResponseEntity<ResponseDto<String>>> sendResetPasswordEmail(@Valid @RequestBody SendEmailRequestDto dto) {
        return mailService.sendResetPasswordEmail(dto);
    }

    @GetMapping(VERIFY_EMAIL)
    public Mono<ResponseEntity<ResponseDto<String>>> verifyEmail(@RequestParam String token) {
        return mailService.verifyEmail(token);
    }

    @PostMapping(RESET_PASSWORD)
    public ResponseEntity<ResponseDto<GetUserInformationToResetPasswordResponseDto>> findUserToResetPassword(@Valid @RequestBody GetUserInformationToResetPasswordRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.findUserToResetPassword(dto));
    }

    @PostMapping(RESET_PASSWORD + "/setting")
    public ResponseEntity<ResponseDto<String>> resetPassword(@RequestParam String token, @Valid @RequestBody ResetPasswordRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.resetPassword(token, dto));
    }
}