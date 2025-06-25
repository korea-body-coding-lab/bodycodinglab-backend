package com.project.bcl_back.controller.coupon;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.coupon.CouponStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequestDto;
import com.project.bcl_back.dto.coupon.response.TrainerCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import com.project.bcl_back.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping(ApiMappingPattern.COUPON_API)
    public ResponseEntity<ResponseDto<Long>> createCoupon(
           @AuthenticationPrincipal Long userId
    ){
        ResponseDto<Long> response = couponService.createCoupon(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
