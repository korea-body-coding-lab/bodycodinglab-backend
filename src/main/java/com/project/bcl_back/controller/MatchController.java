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
import org.springframework.security.access.prepost.PreAuthorize;
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

    private static final String MEMBER_MATCH = "api/v1/users/members/me/match-success-lists";
    private static final String TRAINER_MATCH = "api/v1/users/trainers/me/match-success-lists";


    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_MATCH)
    public ResponseEntity<ResponseDto<TrainerMatchResponseDto>> findMemberMatch(
            @AuthenticationPrincipal Long userId
    ){
        ResponseDto<TrainerMatchResponseDto> response = matchService.findMemberMatch(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(TRAINER_MATCH)
    public ResponseEntity<ResponseDto<List<MemberMatchListResponseDto>>> findMatchTrainerList(
            @AuthenticationPrincipal Long userId
    ){

        ResponseDto<List<MemberMatchListResponseDto>> response = matchService.findMatchTrainerList(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(TRAINER_MATCH + "/{matchId}")
    public ResponseEntity<ResponseDto<MemberMatchResponseDto>> findMatchTrainer(
            @PathVariable Long matchId
    ){
        ResponseDto<MemberMatchResponseDto> response = matchService.findMatchTrainer(matchId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping(MEMBER_MATCH + "/{matchId}")
    public ResponseEntity<ResponseDto<Void>> cancelMatch(
            @PathVariable Long matchId
    ) {
        ResponseDto<Void> response = matchService.cancelMatch(matchId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
