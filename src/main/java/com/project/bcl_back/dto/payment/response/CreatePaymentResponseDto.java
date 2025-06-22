package com.project.bcl_back.dto.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponseDto {
    private Long paymentId;
    private String orderId;
    private int amount;
}
