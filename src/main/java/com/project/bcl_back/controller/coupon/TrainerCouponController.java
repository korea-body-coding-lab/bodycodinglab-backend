package com.project.bcl_back.controller.coupon;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.coupon.CouponStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.request.PutCouponRequestDto;
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
public class TrainerCouponController {
    private final CouponService couponService;

    private static final String SELECT_COUPON = "/{couponId}";

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(ApiMappingPattern.TRAINER_COUPON_API)
    public ResponseEntity<ResponseDto<List<MemberCouponResponseDto>>> findApplicationOrCompleteCoupon(
            @AuthenticationPrincipal Long userId,
            @RequestParam CouponStatus status){

        ResponseDto<List<MemberCouponResponseDto>> response = couponService.findApplicationOrCompleteCoupon(userId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(ApiMappingPattern.TRAINER_COUPON_API + SELECT_COUPON)
    public ResponseEntity<ResponseDto<Void>> trainerPutCoupon(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long couponId,
            @Valid @RequestBody PutCouponRequestDto dto){
        ResponseDto<Void> response  = couponService.trainerPutCoupon(userId, couponId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
