package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.coupon.Status;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequsetDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerApplicationCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerCompleteCouponResponseDto;
import com.project.bcl_back.entity.Coupon;
import com.project.bcl_back.repository.CouponRepository;
import com.project.bcl_back.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public ResponseDto<List<MemberCouponResponseDto>> findNotUsedOrExpiredCoupon(String status) {
        List<MemberCouponResponseDto> coupons = null;

        List<Coupon> notUsedCoupon = couponRepository.findByStatus(status);

        coupons = notUsedCoupon.stream()
                .map(coupon -> new MemberCouponResponseDto(
                        coupon.getCouponId(),
                        coupon.getUser().getUsername(),
                        coupon.getExpirationPeriod(),
                        coupon.getStatus()
                )).collect(Collectors.toList());


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, coupons);
    }

    @Override
    public ResponseDto<List<TrainerApplicationCouponResponseDto>> findApplicationCoupon(String status) {
        List<TrainerApplicationCouponResponseDto> coupons = null;

        List<Coupon> applicationCoupon = couponRepository.findByStatus(status);

        coupons = applicationCoupon.stream()
                .map(coupon -> new TrainerApplicationCouponResponseDto(
                            coupon.getCouponId(),
                            coupon.getUser().getUsername(),
                            coupon.getExpirationPeriod(),
                            coupon.getStatus()
                        )).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, coupons);
    }

    @Override
    public ResponseDto<List<TrainerCompleteCouponResponseDto>> findCompleteCoupon(String status) {
        List<TrainerCompleteCouponResponseDto> coupons = null;

        List<Coupon> completeCoupon = couponRepository.findByStatus(status);

        coupons = completeCoupon.stream()
                .map(coupon -> new TrainerCompleteCouponResponseDto(
                            coupon.getCouponId(),
                            coupon.getUser().getUsername(),
                            coupon.getExpirationPeriod(),
                            coupon.getUsedDate(),
                            coupon.getStatus()
                )).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, coupons);
    }

    @Override
    public ResponseDto<Void> putCoupon(Long couponId, PutCouponRequsetDto dto) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + couponId));

        coupon.setUsedDate(DateUtils.parse(dto.getUsedDate()));
        coupon.setStatus(Status.COMPLETE);

        couponRepository.save(coupon);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);
    }


}
