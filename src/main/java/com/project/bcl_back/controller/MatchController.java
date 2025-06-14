package com.project.bcl_back.controller;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchListResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchResponseDto;
import com.project.bcl_back.dto.match.response.TrainerMatchResponseDto;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    private static final String MEMBER_MATCH = "api/v1/members/me/match-success-lists";
    private static final String TRAINER_MATCH = "api/v1/trainers/me/match-success-lists";


    @GetMapping(MEMBER_MATCH)
    public ResponseEntity<ResponseDto<TrainerMatchResponseDto>> findMatchTrainer(
            @AuthenticationPrincipal User user
    ){
        Long userId = user.getId();
        ResponseDto<TrainerMatchResponseDto> response = matchService.findMatchTrainer(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(TRAINER_MATCH)
    public ResponseEntity<ResponseDto<List<MemberMatchListResponseDto>>> findMatchMemberList(
            @AuthenticationPrincipal User user
    ){
        Long userId = user.getId();
        ResponseDto<List<MemberMatchListResponseDto>> response = matchService.findMatchMemberList(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(TRAINER_MATCH + "/{matchId}")
    public ResponseEntity<ResponseDto<MemberMatchResponseDto>> findMatchMember(
            @PathVariable Long matchId
    ){
        ResponseDto<MemberMatchResponseDto> response = matchService.findMatchMember(matchId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(MEMBER_MATCH + "/{matchId}")
    public ResponseEntity<ResponseDto<Void>> cancelMatch(
            @PathVariable Long matchId
    ) {
        ResponseDto<Void> response = matchService.cancelMatch(matchId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
