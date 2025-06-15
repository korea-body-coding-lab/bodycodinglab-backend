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
    private static final String SIGN_IN = "/login";
    private static final String FIND_ID = "/finding-id";
    private static final String SEND_EMAIL = "/send-email";
    private static final String VERIFY_EMAIL = "/verify";
    private static final String RESET_PASSWORD = "/reset-password";
    private static final String TRAINER_REAPPLY = "/trainer-reapply";


    @PostMapping(SIGN_UP + "/member")
    public ResponseEntity<ResponseDto<SignUpMemberResponseDto>> memberSignup(
            @Valid @RequestPart(value = "dto") SignUpMemberRequestDto dto,
            @RequestPart(value = "profile", required = false) MultipartFile file
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, authService.memberSignup(dto, file));
    }

    @PostMapping(SIGN_UP + "/trainer")
    public ResponseEntity<ResponseDto<SignUpTrainerResponseDto>> trainerSignup(
            @Valid @RequestPart(value = "dto") SignUpTrainerRequestDto dto,
            @RequestPart(value = "attachmentFile") MultipartFile attachmentFile,
            @RequestPart(value = "profile", required = false) MultipartFile profile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, authService.trainerSignup(dto, attachmentFile, profile));
    }

    @PostMapping(SIGN_IN)
    public ResponseEntity<ResponseDto<SignInUserResponseDto>> login(@Valid @RequestBody SignInUserRequestDto dto) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.login(dto));
    }

    @GetMapping(FIND_ID)
    public ResponseEntity<ResponseDto<FindUserIdResponseDto>> findUserId(@Valid @RequestBody FindUserIdRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.findUserId(dto));
    }

    @PostMapping(SEND_EMAIL)
    public Mono<ResponseEntity<String>> sendVerifyEmail(@Valid @RequestBody SendEmailRequestDto dto) {
        return mailService.sendVerifyEmail(dto);
    }

//    @GetMapping(VERIFY_EMAIL)
//    public Mono<ServerResponse> verifyEmail(ServerRequest request) {
//        return mailService.verifyEmail(request);
//    }

    @PostMapping(RESET_PASSWORD)
    public ResponseEntity<ResponseDto<UserInformationToResetPasswordResponseDto>> findUserToResetPassword(@Valid @RequestBody GetUserInformationToResetPasswordRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.findUserToResetPassword(dto));
    }

    @PostMapping(RESET_PASSWORD + "/setting")
    public Mono<ResponseEntity<String>> resetPassword(@RequestParam String email, @Valid @RequestBody ResetPasswordRequestDto dto) {
        return authService.resetPassword(email, dto);
    }

    @PutMapping(TRAINER_REAPPLY)
    public ResponseEntity<ResponseDto<Void>> reapplyTrainer(
            @RequestParam String email,
            @Valid @RequestPart(value = "dto") ReapplyTrainerRequestDto dto,
            @RequestPart(value = "attachmentFile", required = false) MultipartFile attachmentFile
            // 파일 업데이트 기능 완료되면 required = false 제거
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.reapplyTrainer(email, dto, attachmentFile));
    }

}