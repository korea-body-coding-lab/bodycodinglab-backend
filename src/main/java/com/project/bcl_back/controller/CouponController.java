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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("api/v1/coupons")
    public ResponseEntity<ResponseDto<Long>> createCoupon(
           Long memberId
    ){
        ResponseDto<Long> response = couponService.createCoupon(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }





    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(ApiMappingPattern.MEMBER_COUPON_API)
    public ResponseEntity<ResponseDto<List<TrainerCouponResponseDto>>> findNotUsedOrExpiredCoupon(
            @AuthenticationPrincipal Long memberId,
            @RequestParam CouponStatus status
    )
    {
        ResponseDto<List<TrainerCouponResponseDto>> response = couponService.findNotUsedOrExpiredCoupon(memberId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(ApiMappingPattern.TRAINER_COUPON_API)
    public ResponseEntity<ResponseDto<List<MemberCouponResponseDto>>> findApplicationOrCompleteCoupon(
            @AuthenticationPrincipal Long trainerId,
            @RequestParam CouponStatus status){

        ResponseDto<List<MemberCouponResponseDto>> response = couponService.findApplicationOrCompleteCoupon(trainerId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping(ApiMappingPattern.MEMBER_COUPON_API + "/{couponId}")
    public ResponseEntity<ResponseDto<Void>> memberPutCoupon(
            @PathVariable Long couponId
    ){
        ResponseDto<Void> response = couponService.memberPutCoupon(couponId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(ApiMappingPattern.TRAINER_COUPON_API + "/{couponId}")
    public ResponseEntity<ResponseDto<Void>> trainerPutCoupon(
            @PathVariable Long couponId,
            @Valid @RequestBody PutCouponRequestDto dto){
        ResponseDto<Void> response  = couponService.trainerPutCoupon(couponId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
