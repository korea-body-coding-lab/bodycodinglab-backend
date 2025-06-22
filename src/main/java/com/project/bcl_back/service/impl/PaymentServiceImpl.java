package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.payment.PaymentMethod;
import com.project.bcl_back.common.enums.payment.PaymentStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.payment.request.CreatePaymentRequestDto;
import com.project.bcl_back.dto.payment.response.CreatePaymentResponseDto;
import com.project.bcl_back.entity.Member;
import com.project.bcl_back.entity.Payment;
import com.project.bcl_back.repository.PaymentRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    public final UserRepository userRepository;
    public final PaymentRepository paymentRepository;

    @Override
    public ResponseDto<CreatePaymentResponseDto> createPayment(CreatePaymentRequestDto dto, Long userId) {
        Member member = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId))
                .getMember();


        String orderId = "ORDER-" + UUID.randomUUID();

        Payment payment = Payment.builder()
                .orderId(orderId)
                .paymentStatus(PaymentStatus.READY)
                .amount(dto.getAmount())
                .member(member)
                .paymentMethod(PaymentMethod.KAKAO_PAY)
                .build();

        paymentRepository.save(payment);

        CreatePaymentResponseDto response = CreatePaymentResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(orderId)
                .amount(dto.getAmount())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
