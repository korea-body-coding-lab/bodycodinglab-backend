package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.subscription.request.CreateSubscriptionRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import com.project.bcl_back.entity.*;
import com.project.bcl_back.repository.MatchWaitingListRepository;
import com.project.bcl_back.repository.MemberRepository;
import com.project.bcl_back.repository.SubscriptionRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MatchWaitingListRepository matchWaitingListRepository;

    @Override
    @Transactional
    public ResponseDto<Void> createSubscriptionLog(Long userId, Long matchWaitingListId, CreateSubscriptionRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));

        Long memberId = user.getMember().getMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + memberId));

        Subscription subscription = new Subscription(
                null,
                member,
                dto.getPrice(),
                DateUtils.parse(DateUtils.nowFormated())
        );

        MatchWaitingList matchWaitingList = matchWaitingListRepository.findByMember_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException((ResponseMessage.RESOURCE_NOT_FOUND + "매칭 대기 리스트 null")));

        Match match = new Match(
                null,
                matchWaitingList.getMember(),
                matchWaitingList.getTrainer(),
                DateUtils.parse(DateUtils.nowFormated()),
                true
        );

        matchWaitingListRepository.delete(matchWaitingList);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<SubscriptionResponseDto> findSubscription(Long userId) {
        SubscriptionResponseDto response = null;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));

        Long memberId = user.getMember().getMemberId();

        Subscription subscription = subscriptionRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + memberId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + memberId));



        response = new SubscriptionResponseDto(
                member.getUser().getUsername(),
                subscription.getPrice(),
                subscription.getPaymentDate(),
                subscription.getMember().getStatus()
        );


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
