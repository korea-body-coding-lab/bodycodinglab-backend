package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.onedayTicket.OneDayTicketStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.oneDayTicket.request.TicketIssueRequest;
import com.project.bcl_back.dto.oneDayTicket.request.TicketUseRequest;
import com.project.bcl_back.dto.oneDayTicket.response.GetMemberAllTicketsResponseDto;
import com.project.bcl_back.dto.oneDayTicket.response.GetTrainerAllTicketsResponseDto;
import com.project.bcl_back.entity.Member;
import com.project.bcl_back.entity.OneDayTicket;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.*;
import com.project.bcl_back.service.CouponService;
import com.project.bcl_back.service.OneDayTicketService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OneDayTicketServiceImpl implements OneDayTicketService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerInfoRepository trainerInfoRepository;
    private final OneDayTicketRepository oneDayTicketRepository;
    private final CouponServiceImpl couponServiceImpl;

    @Override
    public ResponseDto<List<GetMemberAllTicketsResponseDto>> getMemberAllTickets(Long id) {
        List<GetMemberAllTicketsResponseDto> data = null;
        List<OneDayTicket> tickets = oneDayTicketRepository.findByMemberId(id);

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

    @Override
    public ResponseDto<List<GetTrainerAllTicketsResponseDto>> GetTrainerAllTickets(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }


        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        List<OneDayTicket> tickets = oneDayTicketRepository.findByTrainerId(trainer.getId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_ONE_DAY_TICKET));

        if (tickets == null || tickets.isEmpty()) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_ONE_DAY_TICKET, ResponseMessage.NOT_EXISTS_ONE_DAY_TICKET);
        }

        List<GetTrainerAllTicketsResponseDto> responseDto = tickets.stream()
                .map(ticket -> GetTrainerAllTicketsResponseDto.builder()
                        .id(ticket.getId())
                        .trainerId(ticket.getTrainer().getId())
                        .memberName(ticket.getMember().getName())
                        .memberAddress(ticket.getMember().getMember().getMemberAddress())
                        .issuedAt(ticket.getIssuedAt())
                        .usedAt(ticket.getUsedAt())
                        .canceledAt(ticket.getCanceledAt())
                        .status(ticket.getStatus())
                        .count(ticket.getMember().getMember().getOneDayTicketCount())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    @Transactional
    @Override
    public ResponseDto<Void> issueOneDayTicket(Long id, TicketIssueRequest dto) throws Exception {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        System.out.println("현재 로그인한 유저 ID: " + user.getId());

        TrainerInfo trainer = trainerInfoRepository.findById(user.getTrainerInfo().getId())
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        Member member = memberRepository.findById(dto.getMemberId())
                .orElse(null);

        if (member == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        }

        validateOneDayTicketCount(member);

        OneDayTicket ticket = OneDayTicket.builder()
                .trainer(trainer.getUser())
                .member(member.getUser())
                .issuedAt(LocalDate.now())
                .status(OneDayTicketStatus.ISSUANCE)
                .build();

        oneDayTicketRepository.save(ticket);

        member.minusOneDayTicketCount();
        memberRepository.save(member);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<Void> useOneDayTicket(Long id, Long ticketId, TicketUseRequest dto) throws Exception {
        OneDayTicket ticket = getTicket(ticketId);

        validateTrainer(ticket, id);
        validateTicketStatus(ticket, OneDayTicketStatus.ISSUANCE);

        ticket.setUsedAt(dto.getUsedAt());
        ticket.setStatus(OneDayTicketStatus.USED);

        couponServiceImpl.createCoupon(ticket.getMember().getId());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Transactional
    @Override
    public ResponseDto<Void> cancelOneDayTicket(Long id, Long ticketId) throws Exception {
        OneDayTicket ticket = getTicket(ticketId);

        validateTrainer(ticket, id);
        validateTicketStatus(ticket, OneDayTicketStatus.ISSUANCE);

        ticket.setCanceledAt(LocalDate.now());
        ticket.setStatus(OneDayTicketStatus.CANCEL);

        Member member = ticket.getMember().getMember();
        member.plusOneDayTicketCount();

        memberRepository.save(member);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    private OneDayTicket getTicket(Long ticketId) throws Exception {
        return oneDayTicketRepository.findById(ticketId)
                .orElseThrow(() -> new Exception(ResponseMessage.NOT_EXISTS_ONE_DAY_TICKET));
    }

    private void validateOneDayTicketCount(Member member) throws Exception {
        if (member.getOneDayTicketCount() <= 0) {
            throw new Exception(ResponseMessage.NOT_TRIAL_CHANCE_LEFT);
        }
    }

    private void validateTrainer(OneDayTicket ticket, Long trainerId) throws Exception {
        if (!ticket.getTrainer().getId().equals(trainerId)) {
            throw new Exception(ResponseMessage.NOT_TRIAL_CHANCE_LEFT);
        }
    }

    private void validateTicketStatus(OneDayTicket ticket, OneDayTicketStatus ISSUANCE) throws Exception {
        if (ticket.getStatus() != ISSUANCE) {
            throw new Exception(ResponseMessage.NOT_TRIAL_CHANCE_LEFT);
        }
    }
}
