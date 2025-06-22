package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.project.bcl_back.dto.auth.request.SendEmailRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface MailService {
    Mono<ResponseEntity<ResponseDto<String>>> sendResetPasswordEmail(@Valid SendEmailRequestDto dto);
    Mono<ResponseEntity<ResponseDto<String>>> verifyEmail(String token);
    Mono<ResponseEntity<ResponseDto<String>>> sendTrainerApprovalResultEmail(SendTrainerApprovalResultEmailRequestDto dto);
}
