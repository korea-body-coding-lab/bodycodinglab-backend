package com.project.bcl_back.service;

import com.project.bcl_back.common.enums.onedayTicket.OneDayTicketStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberAllTicketsResponseDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberTicketsByStatusResponseDto;

import java.util.List;

public interface OneDayTicketService {
    ResponseDto<List<GetMemberAllTicketsResponseDto>> getMemberAllTickets(Long id);
    ResponseDto<List<GetMemberTicketsByStatusResponseDto>> getMemberTicketsByStatus(Long id, OneDayTicketStatus status);
}
