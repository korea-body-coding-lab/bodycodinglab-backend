package com.project.bcl_back.dto.coupon.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PutCouponRequsetDto {
    @NotBlank
    private String usedDate;
}
