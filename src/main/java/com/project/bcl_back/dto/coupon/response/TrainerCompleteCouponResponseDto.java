package com.project.bcl_back.dto.coupon.response;

import com.project.bcl_back.common.enums.coupon.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrainerCompleteCouponResponseDto {
    private Long couponId;
    private String trainerName;
    private LocalDate expirationPeriod;
    private LocalDateTime usedDate;
    private CouponStatus status;
}
