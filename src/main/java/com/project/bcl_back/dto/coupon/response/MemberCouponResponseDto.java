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
public class MemberCouponResponseDto {
    private Long couponId;
    private String memberName;
    private LocalDate expirationPeriod;
    private LocalDateTime usedDate;
    private CouponStatus status;

    public MemberCouponResponseDto(Long couponId, String memberName, LocalDate expirationPeriod, CouponStatus status){
        this.couponId = couponId;
        this.memberName = memberName;
        this.expirationPeriod = expirationPeriod;
        this.status = status;
    }
}
