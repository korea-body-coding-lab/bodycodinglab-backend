package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.coupon.CouponStatus;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequestDto;
import com.project.bcl_back.dto.coupon.response.TrainerCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import com.project.bcl_back.entity.Coupon;
import com.project.bcl_back.repository.CouponRepository;
import com.project.bcl_back.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void expireCoupons() {
        LocalDate today = LocalDate.now();
        List<Coupon> couponsToExpire = couponRepository
                .findByExpirationPeriodBeforeAndStatus(today, CouponStatus.NOT_USED);

        for (Coupon coupon : couponsToExpire) {
            coupon.setStatus(CouponStatus.EXPIRED);
        }
    }

    @Override
    public ResponseDto<List<TrainerCouponResponseDto>> findNotUsedOrExpiredCoupon(Long memberId, CouponStatus status) {
        List<TrainerCouponResponseDto> responseCoupons = null;

        List<Coupon> notUsedOrExpiredCoupons = couponRepository.findByStatus(status)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + status));

        List<Coupon> memberCoupons = null;

        memberCoupons = notUsedOrExpiredCoupons.stream()
                .filter(coupon -> memberId.equals(coupon.getMember().getId()))
                .toList();


        responseCoupons = memberCoupons.stream()
                .map(coupon -> new TrainerCouponResponseDto(
                        coupon.getCouponId(),
                        coupon.getTrainer().getName(),
                        coupon.getExpirationPeriod(),
                        coupon.getStatus()
                )).collect(Collectors.toList());


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseCoupons);
    }

    @Override
    public ResponseDto<List<MemberCouponResponseDto>> findApplicationOrCompleteCoupon(Long trainerId, CouponStatus status) {
        List<MemberCouponResponseDto> responseCoupons = null;

        if (status != CouponStatus.APPLICATION && status != CouponStatus.COMPLETE) {
            throw new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + status);
        }


        List<Coupon> applicationOrExpiredCoupons = couponRepository.findByStatus(status)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + status));

        List<Coupon> trainerCoupons = null;

        trainerCoupons = applicationOrExpiredCoupons.stream()
                .filter(coupon -> trainerId.equals(coupon.getTrainer().getId()))
                .toList();

        if(status == CouponStatus.APPLICATION){
            responseCoupons = trainerCoupons.stream()
                    .map(coupon -> new MemberCouponResponseDto(
                            coupon.getCouponId(),
                            coupon.getMember().getUsername(),
                            coupon.getExpirationPeriod(),
                            coupon.getStatus()
                    )).collect(Collectors.toList());
        } else if(status == CouponStatus.COMPLETE){
            responseCoupons = trainerCoupons.stream()
                    .map(coupon -> new MemberCouponResponseDto(
                            coupon.getCouponId(),
                            coupon.getMember().getName(),
                            coupon.getExpirationPeriod(),
                            coupon.getUsedDate(),
                            coupon.getStatus()
                    )).collect(Collectors.toList());
        }



        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseCoupons);
    }

    @Override
    public ResponseDto<Void> memberPutCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + couponId));

        coupon.setStatus(CouponStatus.APPLICATION);

        couponRepository.save(coupon);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }


    @Override
    public ResponseDto<Void> trainerPutCoupon(Long couponId, PutCouponRequestDto dto) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + couponId));

        LocalDateTime usedDate = LocalDate.parse(dto.getUsedDate(), DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();

        coupon.setUsedDate(DateUtils.parse(DateUtils.format(usedDate)));
        coupon.setStatus(CouponStatus.COMPLETE);

        couponRepository.save(coupon);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }


}
