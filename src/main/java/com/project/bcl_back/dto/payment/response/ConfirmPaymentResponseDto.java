package com.project.bcl_back.dto.payment.response;


import com.project.bcl_back.common.enums.payment.PaymentMethod;
import com.project.bcl_back.common.enums.payment.PaymentStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmPaymentResponseDto {
    private Long paymentId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
