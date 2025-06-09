package com.project.bcl_back.dto.coupon.response;

import com.project.bcl_back.common.enums.coupon.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MemberCouponResponseDto {
    private Long couponId;
    private String trainerName;
    private LocalDate expirationPeriod;
    private CouponStatus status;
}
