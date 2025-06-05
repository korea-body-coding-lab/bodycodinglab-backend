package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequsetDto;
import com.project.bcl_back.dto.coupon.response.MemberCouponResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerCouponResponseDto;
import com.project.bcl_back.service.CouponService;
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
    public ResponseEntity<ResponseDto<List<MemberCouponResponseDto>>> findNotUsedCoupon(@RequestParam String status){
        ResponseDto<List<MemberCouponResponseDto>> response = couponService.findNotUsedCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiMappingPattern.TRAINER_COUPON_API)
    public ResponseEntity<ResponseDto<List<TrainerCouponResponseDto>>> findApplicationCoupon(@RequestParam String status){
        ResponseDto<List<TrainerCouponResponseDto>> response = couponService.findApplicationCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(ApiMappingPattern.TRAINER_COUPON_API)
    public ResponseEntity<ResponseDto<List<TrainerCouponResponseDto>>> findCompleteCoupon(@RequestParam String status){
        ResponseDto<List<TrainerCouponResponseDto>> response = couponService.findCompleteCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(ApiMappingPattern.TRAINER_COUPON_API + "/{coupon-id}")
    public ResponseEntity<ResponseDto<Void>> putCoupon(@PathVariable String couponId, @RequestBody PutCouponRequsetDto dto){
        ResponseDto<Void> response = couponService.putCoupon(couponId, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(ApiMappingPattern.MEMBER_COUPON_API)
    public ResponseEntity<ResponseDto<List<MemberCouponResponseDto>>> findExpiredCoupon(@RequestParam String status){
        ResponseDto<List<MemberCouponResponseDto>> response = couponService.findExpiredCoupon(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
