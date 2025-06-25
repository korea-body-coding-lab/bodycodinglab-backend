package com.project.bcl_back.controller.matchWaitingList;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchWaitingListRejectRequestDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchWaitingListRequestDto;
import com.project.bcl_back.dto.matchWatingList.response.MemberMatchWaitingListResponseDto;
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
public class TrainerMatchWaitingListController {

    private final MatchWaitingListService matchWaitingListService;

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(ApiMappingPattern.TRAINER_MATCH_WAITING_LIST_API)
    public ResponseEntity<ResponseDto<List<MemberMatchWaitingListResponseDto>>> findTrainerMatchWaitingList(
            @AuthenticationPrincipal Long userId
    ){

        ResponseDto<List<MemberMatchWaitingListResponseDto>> response
                = matchWaitingListService.findTrainerWaitingList(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(ApiMappingPattern.TRAINER_MATCH_WAITING_LIST_API + "/{matchWaitingListId}/approves")
    public ResponseEntity<ResponseDto<Void>> matchApprove(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long matchWaitingListId,
            @RequestBody MatchWaitingListRequestDto dto
    ){
        ResponseDto<Void> response = matchWaitingListService.matchApprove(userId, matchWaitingListId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(ApiMappingPattern.TRAINER_MATCH_WAITING_LIST_API + "/{matchWaitingListId}/rejects")
    public ResponseEntity<ResponseDto<Void>> matchReject(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long matchWaitingListId,
            @RequestBody MatchWaitingListRejectRequestDto dto
    ){

        ResponseDto<Void> response = matchWaitingListService.matchReject(userId, matchWaitingListId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
