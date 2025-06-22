package com.project.bcl_back.dto.oneDayTicket.response;

import com.project.bcl_back.common.enums.onedayTicket.OneDayTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class GetTrainerAllTicketsResponseDto {
    private Long id;
    private Long trainerId;
    private Long memberId;
    private String memberName;
    private String memberAddress;
    private LocalDate issuedAt;
    private LocalDate usedAt;
    private LocalDate canceledAt;
    private OneDayTicketStatus status;
    private int count;
}
