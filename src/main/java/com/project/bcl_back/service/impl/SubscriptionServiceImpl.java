package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.member.MemberStatus;
import com.project.bcl_back.common.enums.payment.PaymentMethod;
import com.project.bcl_back.common.enums.payment.PaymentStatus;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.payment.request.ConfirmPaymentRequestDto;
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
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public ResponseDto<Long> createSubscriptionLog(Long userId, ConfirmPaymentRequestDto dto) {

        PaymentMethod method = switch (dto.getProvider().toUpperCase()) {
            case "KAKAO_PAY" -> PaymentMethod.KAKAO_PAY;
            default -> throw new IllegalArgumentException("지원하지 않는 결제 수단입니다.");
        };


        Payment payment = paymentRepository.findByOrderId(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("주문 내역이 존재하지 않습니다." + dto.getOrderId()));

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw new IllegalStateException("이미 처리된 결제입니다.");
        }

        payment.setPaymentKey(dto.getPaymentKey());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentMethod(method);


        Member member = payment.getMember();


        Subscription subscription = Subscription.builder()
                .member(member)
                .price(payment.getAmount())
                .paymentDate(DateUtils.parse(DateUtils.nowFormated()))
                .build();

        payment.setSubscription(subscription);
        member.setSubscription(subscription);
        member.setStatus(MemberStatus.PAYMENT);
        subscriptionRepository.save(subscription);
        paymentRepository.save(payment);



        MatchWaitingList matchWaitingList = matchWaitingListRepository.findByMember_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + userId));

        User trainer = userRepository.findById(matchWaitingList.getTrainer().getId())
                .orElseThrow(() ->
                        new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + matchWaitingList.getTrainer().getId()));


        Match match = Match.builder()
                .member(matchWaitingList.getMember())
                .trainer(trainer)
                .matchedAt(DateUtils.parse(DateUtils.nowFormated()))
                .isMaintained (true)
                .build();



        match.getMember().setMemberMatch(match);
        trainer.addTrainerMatches(match);
        matchRepository.save(match);


        match.getMember().setMatchWaitingListAsMember(null);
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
