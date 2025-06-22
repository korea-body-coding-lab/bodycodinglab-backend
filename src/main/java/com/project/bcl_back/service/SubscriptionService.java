package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.payment.request.ConfirmPaymentRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    ResponseDto<Long> createSubscriptionLog(Long userId, ConfirmPaymentRequestDto dto);

    ResponseDto<SubscriptionResponseDto> findSubscription(Long userId);
}
