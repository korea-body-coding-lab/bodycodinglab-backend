package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.member.MemberStatus;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.subscription.request.CreateSubscriptionRequestDto;
import com.project.bcl_back.dto.subscription.response.SubscriptionResponseDto;
import com.project.bcl_back.entity.*;
import com.project.bcl_back.repository.*;
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
    private final MatchRepository matchRepository;

    @Override
    @Transactional
    public ResponseDto<Long> createSubscriptionLog(Long userId, Long matchWaitingListId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));

        Long memberId = user.getMember().getMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + memberId));

        Subscription subscription = new Subscription(
                null,
                member,
                149000,
                DateUtils.parse(DateUtils.nowFormated())
        );

        member.setSubscription(subscription);
        member.setStatus(MemberStatus.PAYMENT);
        subscriptionRepository.save(subscription);

        MatchWaitingList matchWaitingList = matchWaitingListRepository.findByMember_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + userId));

        User trainer = userRepository.findById(matchWaitingList.getTrainer().getId())
                .orElseThrow(() ->
                        new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + matchWaitingList.getTrainer().getId()));

        Match match = new Match(
                null,
                matchWaitingList.getMember(),
                matchWaitingList.getTrainer(),
                DateUtils.parse(DateUtils.nowFormated()),
                true
        );

        user.setMemberMatch(match);
        trainer.addTrainerMatches(match);
        matchRepository.save(match);

        user.setMatchWaitingListAsMember(null);
        trainer.removeMatchWaitingListAsTrainers(matchWaitingList);
        matchWaitingListRepository.delete(matchWaitingList);


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, subscription.getPaymentId());
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
                member.getUser().getName(),
                subscription.getPrice(),
                subscription.getPaymentDate(),
                subscription.getMember().getStatus()
        );


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
