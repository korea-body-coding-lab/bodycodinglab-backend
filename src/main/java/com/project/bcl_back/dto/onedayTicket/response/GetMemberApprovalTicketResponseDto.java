package com.project.bcl_back.dto.onedayTicket.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class GetMemberApprovalTicketResponseDto {
    private Long ticketId;
    private String trainerName;
    private String address;
    private LocalDateTime processedAt;
}
