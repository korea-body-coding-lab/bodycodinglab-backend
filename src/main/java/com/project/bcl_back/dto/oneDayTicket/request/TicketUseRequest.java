package com.project.bcl_back.dto.oneDayTicket.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class TicketUseRequest {
    private LocalDate usedAt;
}
