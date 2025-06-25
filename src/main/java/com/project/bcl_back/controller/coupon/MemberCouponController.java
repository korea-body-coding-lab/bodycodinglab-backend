package com.project.bcl_back.controller.coupon;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.coupon.CouponStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.coupon.response.TrainerCouponResponseDto;
import com.project.bcl_back.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberCouponController {
    private final CouponService couponService;

    private static final String SELECT_COUPON = "/{couponId}";

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(ApiMappingPattern.MEMBER_COUPON_API)
    public ResponseEntity<ResponseDto<List<TrainerCouponResponseDto>>> findNotUsedOrExpiredCoupon(
            @AuthenticationPrincipal Long userId,
            @RequestParam CouponStatus status
    )
    {
        ResponseDto<List<TrainerCouponResponseDto>> response = couponService.findNotUsedOrExpiredCoupon(userId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping(ApiMappingPattern.MEMBER_COUPON_API + SELECT_COUPON)
    public ResponseEntity<ResponseDto<Void>> memberPutCoupon(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long couponId
    ){
        ResponseDto<Void> response = couponService.memberPutCoupon(userId, couponId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
