package com.project.bcl_back.dto.subscription.response;

import com.project.bcl_back.common.enums.member.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubscriptionResponseDto {
    private String memberName;
    private int price;
    private LocalDateTime paymentDate;
    private MemberStatus status;
}
