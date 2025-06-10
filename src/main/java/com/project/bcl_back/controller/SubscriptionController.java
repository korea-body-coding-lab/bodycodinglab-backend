package com.project.bcl_back.controller;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.subscription.request.CreateSubscriptionRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import com.project.bcl_back.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/api/v1/members/trainers/{trainer-id}/subscriptions/{member-id}")
    public ResponseEntity<ResponseDto<Void>> createSubscriptionLog(
            @PathVariable Long trainerId,
            @PathVariable Long memberId,
            @Valid @RequestBody CreateSubscriptionRequestDto dto
            ){
        ResponseDto<Void> response = subscriptionService.createSubscriptionLog(trainerId, memberId ,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/v1/members/me/subscriptions/{memberId}")
    public ResponseEntity<ResponseDto<SubscriptionResponseDto>> findSubscription(
            @PathVariable Long memberId
    ){
        ResponseDto<SubscriptionResponseDto> response = subscriptionService.findSubscription(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
