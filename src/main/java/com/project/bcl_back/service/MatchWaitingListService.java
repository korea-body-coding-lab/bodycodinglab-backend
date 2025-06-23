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

    ResponseDto<List<MemberMatchWaitingListResponseDto>> findTrainerWaitingList(Long trainerId);

    ResponseDto<TrainerMatchWaitingListResponseDto> findMemberMatchWaitingList(Long memberId);

    ResponseDto<Void> matchReject(Long matchWaitingListId, MatchWaitingListRejectRequestDto dto);

    ResponseDto<Void> matchCancel(Long memberId);

    ResponseDto<Void> matchApprove(Long matchWaitingListId, MatchWaitingListRequestDto dto);
}
