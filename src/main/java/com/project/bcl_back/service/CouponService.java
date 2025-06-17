package com.project.bcl_back.service;

import com.project.bcl_back.common.enums.coupon.CouponStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequestDto;
import com.project.bcl_back.dto.coupon.response.TrainerCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponService {
    ResponseDto<List<TrainerCouponResponseDto>> findNotUsedOrExpiredCoupon(Long memberId, CouponStatus status);

    ResponseDto<List<MemberCouponResponseDto>> findApplicationOrCompleteCoupon(Long trainerId, CouponStatus status);

    ResponseDto<Void> memberPutCoupon(Long couponId);


    ResponseDto<Void> trainerPutCoupon(Long couponId, PutCouponRequestDto dto);


}
