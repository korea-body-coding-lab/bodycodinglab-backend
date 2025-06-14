package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.onedayTicket.OneDayTicketStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberAllTicketsResponseDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberTicketsByStatusResponseDto;
import com.project.bcl_back.service.OneDayTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OneDayTicketController {
    private final OneDayTicketService oneDayTicketService;

    private static final String MEMBER_ONE_DAY_TICKET = ApiMappingPattern.USER_API + "/members/me/one-day-tickets";

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_ONE_DAY_TICKET)
    public ResponseEntity<ResponseDto<List<GetMemberAllTicketsResponseDto>>> getMemberAllTickets(@AuthenticationPrincipal Long id) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, oneDayTicketService.getMemberAllTickets(id));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_ONE_DAY_TICKET + "/status")
    public ResponseEntity<ResponseDto<List<GetMemberTicketsByStatusResponseDto>>> getMemberTicketsByStatus(
            @AuthenticationPrincipal Long id,
            @RequestParam OneDayTicketStatus status
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, oneDayTicketService.getMemberTicketsByStatus(id, status));
    }
}
