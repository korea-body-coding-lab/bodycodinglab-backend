package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.subscription.request.CreateSubscriptionRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import com.project.bcl_back.entity.Member;
import com.project.bcl_back.entity.Subscription;
import com.project.bcl_back.repository.MemberRepository;
import com.project.bcl_back.repository.SubscriptionRepository;
import com.project.bcl_back.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;


    @Override
    public ResponseDto<Void> createSubscriptionLog(Long trainerId, Long memberId, CreateSubscriptionRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<SubscriptionResponseDto> findSubscription(Long memberId) {
        SubscriptionResponseDto response = null;

        Subscription subscription = subscriptionRepository.findByMemberId(memberId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + memberId));



        response = new SubscriptionResponseDto(
                member.getUser().getUsername(),
                subscription.getPrice(),
                subscription.getSubscriptionName(),
                subscription.getPaymentDate(),
                subscription.getMemberSubscribeDate(),
                subscription.getMember().getStatus()
        );


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
