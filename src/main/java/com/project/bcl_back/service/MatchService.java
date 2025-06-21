package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchListResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchResponseDto;
import com.project.bcl_back.dto.match.response.TrainerMatchResponseDto;
import com.project.bcl_back.entity.Match;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MatchService {
    ResponseDto<TrainerMatchResponseDto> findMemberMatch(Long userId);

    ResponseDto<List<MemberMatchListResponseDto>> findMatchTrainerList(Long userId);

    ResponseDto<MemberMatchResponseDto> findMatchTrainer(Long matchId);

    ResponseDto<Void> cancelMatch(Long matchId);

    Long findByUserId(Long userId, String role);
}
