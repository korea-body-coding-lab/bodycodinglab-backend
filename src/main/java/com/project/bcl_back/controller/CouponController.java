package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.coupon.CouponStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequestDto;
import com.project.bcl_back.dto.coupon.response.TrainerCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping(ApiMappingPattern.MEMBER_COUPON_API)
    public ResponseEntity<ResponseDto<List<TrainerCouponResponseDto>>> findNotUsedOrExpiredCCoupon(
            @AuthenticationPrincipal User user,
            @RequestParam CouponStatus status
    )
    {
        Long memberId = user.getId();
        ResponseDto<List<TrainerCouponResponseDto>> response = couponService.findNotUsedOrExpiredCoupon(memberId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiMappingPattern.TRAINER_COUPON_API)
    public ResponseEntity<ResponseDto<List<MemberCouponResponseDto>>> findApplicationOrCompleteCoupon(
            @AuthenticationPrincipal User user,
            @RequestParam CouponStatus status){
        Long trainerId = user.getId();
        ResponseDto<List<MemberCouponResponseDto>> response = couponService.findApplicationOrCompleteCoupon(trainerId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping(ApiMappingPattern.TRAINER_COUPON_API + "/{couponId}")
    public ResponseEntity<ResponseDto<Void>> putCoupon(
            @PathVariable Long couponId,
            @Valid @RequestBody PutCouponRequestDto dto){
        ResponseDto<Void> response  = couponService.putCoupon(couponId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
