package com.project.bcl_back.controller;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchWaitingListRequestDto;
import com.project.bcl_back.dto.matchWatingList.response.MemberMatchWaitingListResponseDto;
import com.project.bcl_back.dto.matchWatingList.response.TrainerMatchWaitingListResponseDto;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.service.MatchWaitingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchWaitListController {

    private final MatchWaitingListService matchWaitingListService;

    private static final String TRAINER_MATCH_WAITING_LIST = "/api/v1/users/trainers/me/match-waiting-lists";
    private static final String MEMBER_MATCH_WAITING_LIST = "/api/v1/users/members/me/match-waiting-lists";

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/api/v1/users/members/trainers/{trainerId}/match-waiting-lists")
    public ResponseEntity<ResponseDto<Long>> createMatchWaitingList(
            @PathVariable Long trainerId,
            @AuthenticationPrincipal Long userId
    ){
        ResponseDto<Long> response = matchWaitingListService.createMatchWaitingList(trainerId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(TRAINER_MATCH_WAITING_LIST)
    public ResponseEntity<ResponseDto<List<MemberMatchWaitingListResponseDto>>> findTrainerMatchWaitingList(
            @AuthenticationPrincipal Long trainerId
    ){

        ResponseDto<List<MemberMatchWaitingListResponseDto>> response
                = matchWaitingListService.findTrainerWaitingList(trainerId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_MATCH_WAITING_LIST)
    public ResponseEntity<ResponseDto<TrainerMatchWaitingListResponseDto>> findTMemberMatchWaitingList(
            @AuthenticationPrincipal Long memberId
    )
    {
        ResponseDto<TrainerMatchWaitingListResponseDto> response = matchWaitingListService.findMemberMatchWaitingList(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(TRAINER_MATCH_WAITING_LIST + "/{matchWaitingListId}/approves")
    public ResponseEntity<ResponseDto<Void>> matchApprove(
            @PathVariable Long matchWaitingListId,
            @RequestBody MatchWaitingListRequestDto dto
    ){
        ResponseDto<Void> response = matchWaitingListService.matchApprove(matchWaitingListId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(TRAINER_MATCH_WAITING_LIST + "/{matchWaitingListId}/rejects")
    public ResponseEntity<ResponseDto<Void>> matchReject(
            @PathVariable Long matchWaitingListId,
            @RequestBody MatchWaitingListRequestDto dto
    ){

        ResponseDto<Void> response = matchWaitingListService.matchReject(matchWaitingListId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping(MEMBER_MATCH_WAITING_LIST + "/cancels")
    public ResponseEntity<ResponseDto<Void>> matchCancel(
            @AuthenticationPrincipal Long memberId,
            @RequestBody MatchWaitingListRequestDto dto
    ){

        ResponseDto<Void> response =  matchWaitingListService.matchCancel(memberId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
