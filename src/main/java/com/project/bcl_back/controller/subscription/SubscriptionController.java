package com.project.bcl_back.controller.subscription;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.payment.request.ConfirmPaymentRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import com.project.bcl_back.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    private static final String POST_SUBSCRIPTION = "/subscriptions";

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping( ApiMappingPattern.MEMBER_MATCH_WAITING_LIST_API + POST_SUBSCRIPTION)
    public ResponseEntity<ResponseDto<Long>> createSubscriptionLog(
            @AuthenticationPrincipal Long userId,
            @RequestBody ConfirmPaymentRequestDto dto
    ){
        ResponseDto<Long> response = subscriptionService.createSubscriptionLog(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(ApiMappingPattern.SUBSCRIPTION_API)
    public ResponseEntity<ResponseDto<SubscriptionResponseDto>> findSubscription(
            @AuthenticationPrincipal Long userId
    ){
        ResponseDto<SubscriptionResponseDto> response = subscriptionService.findSubscription(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
