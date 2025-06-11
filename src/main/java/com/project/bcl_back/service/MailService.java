package com.project.bcl_back.service;

import com.project.bcl_back.dto.auth.request.SendEmailRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface MailService {
    Mono<ResponseEntity<String>> sendVerifyEmail(@Valid SendEmailRequestDto dto);
    Mono<ResponseEntity<String>> verifyEmail(String token);
}
