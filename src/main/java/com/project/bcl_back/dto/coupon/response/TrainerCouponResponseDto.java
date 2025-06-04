package com.project.bcl_back.dto.coupon.response;

import com.project.bcl_back.common.enums.coupon.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrainerCouponResponseDto {
    private Long couponId;
    private String trainerName;
    private LocalDate expirationPeriod;
    private LocalDateTime usedDate;
    private Status status;
}
