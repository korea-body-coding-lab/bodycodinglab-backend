package com.project.bcl_back.dto.subscription.response;

import com.project.bcl_back.common.enums.member.Status;

import java.time.LocalDateTime;

public class SubscriptionResponseDto {
    private String memberName;
    private Integer price;
    private String subscriptionName;
    private LocalDateTime paymentDate;
    private LocalDateTime memberSubscriptionDate;
    private Status staus;
}
