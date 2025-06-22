package com.project.bcl_back.controller;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.payment.request.CreatePaymentRequestDto;
import com.project.bcl_back.dto.payment.response.CreatePaymentResponseDto;
import com.project.bcl_back.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/v1/users/members/me/match-waiting-lists/payments")
    public ResponseEntity<ResponseDto<CreatePaymentResponseDto>> createPayment(
            @RequestBody CreatePaymentRequestDto dto,
            @AuthenticationPrincipal Long userId
    ) {
        ResponseDto<CreatePaymentResponseDto> response = paymentService.createPayment(dto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
