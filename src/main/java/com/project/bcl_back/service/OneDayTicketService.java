package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.oneDayTicket.request.TicketCancelRequest;
import com.project.bcl_back.dto.oneDayTicket.request.TicketIssueRequest;
import com.project.bcl_back.dto.oneDayTicket.request.TicketUseRequest;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberAllTicketsResultDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetTrainerAllTicketsResponseDto;

import java.util.List;

public interface OneDayTicketService {
    ResponseDto<GetMemberAllTicketsResultDto> getMemberAllTickets(Long id);
    ResponseDto<List<GetTrainerAllTicketsResponseDto>> GetTrainerAllTickets(Long id);
    ResponseDto<Void> issueOneDayTicket(Long id, TicketIssueRequest dto) throws Exception;
    ResponseDto<Void> useOneDayTicket(Long id, Long ticketId, TicketUseRequest dto) throws Exception;
    ResponseDto<Void> cancelOneDayTicket(Long id, Long ticketId, TicketCancelRequest dto) throws Exception;
}
