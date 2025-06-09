package com.project.bcl_back.dto.subscription.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateSubscriptionRequestDto {
    @NotBlank
    private int price;
}
