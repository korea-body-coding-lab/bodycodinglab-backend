package com.project.bcl_back.controller;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchRejectRequestDto;
import com.project.bcl_back.dto.matchWatingList.response.MemberMatchWaitingListResponseDto;
import com.project.bcl_back.dto.matchWatingList.response.TrainerMatchWaitingListResponseDto;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.service.MatchWaitingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchWaitListController {

    private final MatchWaitingListService matchWaitingListService;

    private static final String TRAINER_MATCH_WAITING_LIST = "/api/v1/trainers/me/match-waiting-lists";
    private static final String MEMBER_MATCH_WAITING_LIST = "/api/v1/members/me/match-waiting-lists";

    @PostMapping("/api/v1/members/trainers/{trainerId}/match-waiting-lists")
    public ResponseEntity<ResponseDto<Void>> createMatchWaitingList(
            @PathVariable Long trainerId,
            @AuthenticationPrincipal User user
    ){
        Long userId = user.getId();
        ResponseDto<Void> response = matchWaitingListService.createMatchWaitingList(trainerId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(TRAINER_MATCH_WAITING_LIST)
    public ResponseEntity<ResponseDto<List<MemberMatchWaitingListResponseDto>>> findMemberMatchWaitingList(
            @AuthenticationPrincipal User user
    ){
        Long trainerId = user.getId();
        ResponseDto<List<MemberMatchWaitingListResponseDto>> response
                = matchWaitingListService.findMemberWaitingList(trainerId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(MEMBER_MATCH_WAITING_LIST)
    public ResponseEntity<ResponseDto<TrainerMatchWaitingListResponseDto>> findTrainerMatchWaitingList(
            @AuthenticationPrincipal User user
    ){
        Long memberId = user.getId();
        ResponseDto<TrainerMatchWaitingListResponseDto> response = matchWaitingListService.findTrainerMatchWaitingList(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(TRAINER_MATCH_WAITING_LIST + "/{matchWaitingListId}/rejects")
    public ResponseEntity<ResponseDto<Void>> matchReject(
            @PathVariable Long matchWaitingListId,
            @RequestBody MatchRejectRequestDto dto
    ){

        matchWaitingListService.matchReject(matchWaitingListId, dto);

        return ResponseEntity.noContent().build();
    }
}
