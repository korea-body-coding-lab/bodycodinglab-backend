package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchWaitingListRequestDto;
import com.project.bcl_back.dto.matchWatingList.response.MemberMatchWaitingListResponseDto;
import com.project.bcl_back.dto.matchWatingList.response.TrainerMatchWaitingListResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchWaitingListService {
    ResponseDto<Void> createMatchWaitingList(Long trainerId, Long userId);

    ResponseDto<List<MemberMatchWaitingListResponseDto>> findMemberWaitingList(Long trainerId);

    ResponseDto<TrainerMatchWaitingListResponseDto> findTrainerMatchWaitingList(Long memberId);

    ResponseDto<Void> matchReject(Long matchWaitingListId, MatchWaitingListRequestDto dto);

    ResponseDto<Void> matchCancel(Long memberId, MatchWaitingListRequestDto dto);

    ResponseDto<Void> matchApprove(Long matchWaitingListId, MatchWaitingListRequestDto dto);
}
