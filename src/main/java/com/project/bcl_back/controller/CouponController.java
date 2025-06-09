package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
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
    public ResponseEntity<ResponseDto<List<MemberCouponResponseDto>>> findNotUsedOrExpiredCCoupon(@RequestParam String status){
        ResponseDto<List<MemberCouponResponseDto>> response = couponService.findNotUsedOrExpiredCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiMappingPattern.TRAINER_COUPON_API)
    public ResponseEntity<ResponseDto<List<TrainerApplicationCouponResponseDto>>> findApplicationCoupon(@RequestParam String status){
        ResponseDto<List<TrainerApplicationCouponResponseDto>> response = couponService.findApplicationCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiMappingPattern.TRAINER_COUPON_API)
    public ResponseEntity<ResponseDto<List<TrainerCompleteCouponResponseDto>>> findCompleteCoupon(@RequestParam String status){
        ResponseDto<List<TrainerCompleteCouponResponseDto>> response = couponService.findCompleteCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(ApiMappingPattern.TRAINER_COUPON_API + "/{coupon-id}")
    public ResponseEntity<ResponseDto<Void>> putCoupon(@PathVariable Long couponId, @Valid @RequestBody PutCouponRequsetDto dto){
            couponService.putCoupon(couponId, dto);
        return ResponseEntity.noContent().build();
    }


}
