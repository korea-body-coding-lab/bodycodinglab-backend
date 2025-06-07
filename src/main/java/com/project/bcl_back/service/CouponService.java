package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequsetDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerApplicationCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerCompleteCouponResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponService {
    ResponseDto<List<MemberCouponResponseDto>> findNotUsedOrExpiredCoupon(String status);

    ResponseDto<List<TrainerApplicationCouponResponseDto>> findApplicationCoupon(String status);

    ResponseDto<List<TrainerCompleteCouponResponseDto>> findCompleteCoupon(String status);

    ResponseDto<Void> putCoupon(Long couponId, PutCouponRequsetDto dto);


}
