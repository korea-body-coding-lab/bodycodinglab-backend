package com.project.bcl_back.dto.oneDayTicket.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class GetMemberTicketsByStatusResponseDto {
    private Long id;
    private String trainerName;
    private String jobAddress;
    private LocalDate statusDate;
    private int count;
}
