package com.project.bcl_back.dto.onedayTicket.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class GetMemberTicketsResponseDto {
    private Long ticketId;
    private String trainerName;
    private String address;
    private LocalDateTime appliedAt;
    private LocalDateTime processedAt;
    private LocalDateTime usedAt;
    private String status;
}
