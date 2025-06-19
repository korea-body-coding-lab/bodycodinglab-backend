package com.project.bcl_back.controller;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.subscription.request.CreateSubscriptionRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/api/v1/users/members/me/match-waiting-lists/{matchWaitingListId}/subscriptions")
    public ResponseEntity<ResponseDto<Long>> createSubscriptionLog(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long matchWaitingListId
    ){
        ResponseDto<Long> response = subscriptionService.createSubscriptionLog(userId, matchWaitingListId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/v1/users/members/me/subscriptions")
    public ResponseEntity<ResponseDto<SubscriptionResponseDto>> findSubscription(
            @AuthenticationPrincipal Long userId
    ){
        ResponseDto<SubscriptionResponseDto> response = subscriptionService.findSubscription(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
