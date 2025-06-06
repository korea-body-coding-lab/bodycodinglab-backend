package com.project.bcl_back.dto.coupon.response;

import com.project.bcl_back.common.enums.coupon.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class TrainerApplicationCouponResponseDto {
    private Long couponId;
    private String memberName;
    private LocalDate expirationPeriod;
    private Status status;
}
