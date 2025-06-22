package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.payment.request.CreatePaymentRequestDto;
import com.project.bcl_back.dto.payment.response.CreatePaymentResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    ResponseDto<CreatePaymentResponseDto> createPayment(CreatePaymentRequestDto dto, Long userId);
}
