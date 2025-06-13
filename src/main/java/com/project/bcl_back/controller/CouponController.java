package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.coupon.CouponStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequsetDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerApplicationCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerCompleteCouponResponseDto;
import com.project.bcl_back.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping(ApiMappingPattern.MEMBER_COUPON_API)
    public ResponseEntity<ResponseDto<List<MemberCouponResponseDto>>> findNotUsedOrExpiredCCoupon(@RequestParam CouponStatus status){
        ResponseDto<List<MemberCouponResponseDto>> response = couponService.findNotUsedOrExpiredCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiMappingPattern.TRAINER_COUPON_API)
    public ResponseEntity<ResponseDto<List<TrainerApplicationCouponResponseDto>>> findApplicationOrCompleteCoupon(@RequestParam CouponStatus status){
        ResponseDto<List<TrainerApplicationCouponResponseDto>> response = couponService.findApplicationOrCompleteCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @PutMapping(ApiMappingPattern.TRAINER_COUPON_API + "/{couponId}")
    public ResponseEntity<ResponseDto<Void>> putCoupon(@PathVariable Long couponId, @Valid @RequestBody PutCouponRequsetDto dto){
           ResponseDto<Void> response = couponService.putCoupon(couponId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
