package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.subscription.request.CreateSubscriptionRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    ResponseDto<Void> createSubscriptionLog(Long trainerId, Long memberId, CreateSubscriptionRequestDto dto);

    ResponseDto<SubscriptionResponseDto> findSubscription(Long memberId);
}
