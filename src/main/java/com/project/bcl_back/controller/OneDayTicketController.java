package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.oneDayTicket.request.TicketCancelRequest;
import com.project.bcl_back.dto.oneDayTicket.request.TicketIssueRequest;
import com.project.bcl_back.dto.oneDayTicket.request.TicketUseRequest;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberAllTicketsResultDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetTrainerAllTicketsResponseDto;
import com.project.bcl_back.service.OneDayTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OneDayTicketController {
    private final OneDayTicketService oneDayTicketService;

    private static final String MEMBER_ONE_DAY_TICKET = ApiMappingPattern.USER_API + "/members/me/one-day-tickets";
    private static final String TRAINER_ONE_DAY_TICKET = ApiMappingPattern.TRAINER_API + "/me/one-day-tickets";

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_ONE_DAY_TICKET)
    public ResponseEntity<ResponseDto<GetMemberAllTicketsResultDto>> getMemberAllTickets(@AuthenticationPrincipal Long id) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, oneDayTicketService.getMemberAllTickets(id));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(TRAINER_ONE_DAY_TICKET)
    public ResponseEntity<ResponseDto<List<GetTrainerAllTicketsResponseDto>>> GetTrainerAllTickets(
            @AuthenticationPrincipal Long id
    ){
        ResponseDto<List<GetTrainerAllTicketsResponseDto>> response = oneDayTicketService.GetTrainerAllTickets(id);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(TRAINER_ONE_DAY_TICKET + "/issued")
    public ResponseEntity<ResponseDto<Void>> issueOneDayTicket(
            @AuthenticationPrincipal Long id,
            @RequestBody TicketIssueRequest dto) throws Exception {
        ResponseDto<Void> response = oneDayTicketService.issueOneDayTicket(id, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(TRAINER_ONE_DAY_TICKET + "/{ticketId}/used")
    public ResponseEntity<ResponseDto<Void>> useOneDayTicket(
            @AuthenticationPrincipal Long id,
            @PathVariable Long ticketId,
            @RequestBody TicketUseRequest dto) throws Exception {
        ResponseDto<Void> response = oneDayTicketService.useOneDayTicket(id, ticketId, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(TRAINER_ONE_DAY_TICKET + "/{ticketId}/canceled")
    public ResponseEntity<ResponseDto<Void>> cancelOneDayTicket(
            @AuthenticationPrincipal Long id,
            @PathVariable Long ticketId,
            @RequestBody TicketCancelRequest dto) throws Exception {
        ResponseDto<Void> response = oneDayTicketService.cancelOneDayTicket(id, ticketId, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}
