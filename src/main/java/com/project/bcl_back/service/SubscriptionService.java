package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.subscription.request.CreateSubscriptionRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    ResponseDto<Void> createSubscriptionLog(Long userId, Long matchWaitingListId, @Valid CreateSubscriptionRequestDto dto);

    ResponseDto<SubscriptionResponseDto> findSubscription(Long userId);
}
