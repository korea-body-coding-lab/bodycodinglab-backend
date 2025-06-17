package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberAllTicketsResponseDto;

import java.util.List;

public interface OneDayTicketService {
    ResponseDto<List<GetMemberAllTicketsResponseDto>> getMemberAllTickets(Long id);
}
