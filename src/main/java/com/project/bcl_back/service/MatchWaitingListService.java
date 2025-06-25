package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchWaitingListRejectRequestDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchWaitingListRequestDto;
import com.project.bcl_back.dto.matchWatingList.response.MemberMatchWaitingListResponseDto;
import com.project.bcl_back.dto.matchWatingList.response.TrainerMatchWaitingListResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchWaitingListService {
    ResponseDto<Long> createMatchWaitingList(Long trainerId, Long userId);

    ResponseDto<List<MemberMatchWaitingListResponseDto>> findTrainerWaitingList(Long userId);

    ResponseDto<TrainerMatchWaitingListResponseDto> findMemberMatchWaitingList(Long userId);

    ResponseDto<Void> matchReject( Long userId, Long matchWaitingListId, MatchWaitingListRejectRequestDto dto);

    ResponseDto<Void> matchCancel(Long userId);

    ResponseDto<Void> matchApprove(Long userId, Long matchWaitingListId, MatchWaitingListRequestDto dto);
}
