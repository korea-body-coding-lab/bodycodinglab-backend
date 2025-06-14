package com.project.bcl_back.dto.coupon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PutCouponRequestDto {
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String usedDate;
}
