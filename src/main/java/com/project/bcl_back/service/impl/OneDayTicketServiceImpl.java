package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberAllTicketsResponseDto;
import com.project.bcl_back.entity.OneDayTicket;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.OneDayTicketRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.OneDayTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OneDayTicketServiceImpl implements OneDayTicketService {
    private final UserRepository userRepository;
    private final OneDayTicketRepository onedayTicketRepository;

    @Override
    public ResponseDto<List<GetMemberAllTicketsResponseDto>> getMemberAllTickets(Long id) {
        List<GetMemberAllTicketsResponseDto> data = null;
        List<OneDayTicket> tickets = onedayTicketRepository.findByMemberId(id);

        if (tickets == null || tickets.isEmpty()) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_ONE_DAY_TICKET, ResponseMessage.NOT_EXISTS_ONE_DAY_TICKET);
        }

        data = tickets.stream()
                .map(ticket -> GetMemberAllTicketsResponseDto.builder()
                        .id(ticket.getId())
                        .trainerId(ticket.getTrainer().getId())
                        .trainerName(ticket.getTrainer().getName())
                        .jobAddress(ticket.getTrainer().getTrainerInfo().getJobAddress())
                        .issuedAt(ticket.getIssuedAt())
                        .usedAt(ticket.getUsedAt())
                        .canceledAt(ticket.getCanceledAt())
                        .status(ticket.getStatus())
                        .count(ticket.getMember().getMember().getOneDayTicketCount())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }
}
