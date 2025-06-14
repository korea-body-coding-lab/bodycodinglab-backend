package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchListResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchResponseDto;
import com.project.bcl_back.dto.match.response.TrainerMatchResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchService {
    ResponseDto<TrainerMatchResponseDto> findMatchTrainer(Long userId);

    ResponseDto<List<MemberMatchListResponseDto>> findMatchMemberList(Long userId);

    ResponseDto<MemberMatchResponseDto> findMatchMember(Long matchId);

    ResponseDto<Void> cancelMatch(Long matchId);
}
