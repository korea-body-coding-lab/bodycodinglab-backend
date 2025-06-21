package com.project.bcl_back.dto.oneDayTicket.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TicketIssueRequest {
    private Long memberId;
    private Long trainerId;
}
