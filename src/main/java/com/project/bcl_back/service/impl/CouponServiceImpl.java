package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequsetDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerCouponResponseDto;
import com.project.bcl_back.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Override
    public ResponseDto<List<MemberCouponResponseDto>> findNotUsedCoupon(String status) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerCouponResponseDto>> findApplicationCoupon(String status) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerCouponResponseDto>> findCompleteCoupon(String status) {
        return null;
    }

    @Override
    public ResponseDto<Void> putCoupon(String couponId, PutCouponRequsetDto dto) {
        return null;
    }

    @Override
    public ResponseDto<List<MemberCouponResponseDto>> findExpiredCoupon(String status) {
        return null;
    }
}
