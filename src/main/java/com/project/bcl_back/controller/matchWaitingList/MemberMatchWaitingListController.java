package com.project.bcl_back.controller.matchWaitingList;


import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.matchWatingList.response.TrainerMatchWaitingListResponseDto;
import com.project.bcl_back.service.MatchWaitingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberMatchWaitingListController {
    private final MatchWaitingListService matchWaitingListService;

    private static final String CANCEL = "/cancels";

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/api/v1/users/members/trainer/{trainerId}/match-waiting-lists") // 보류
    public ResponseEntity<ResponseDto<Long>> createMatchWaitingList(
            @PathVariable Long trainerId,
            @AuthenticationPrincipal Long userId
    ){
        ResponseDto<Long> response = matchWaitingListService.createMatchWaitingList(trainerId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(ApiMappingPattern.MEMBER_MATCH_WAITING_LIST_API)
    public ResponseEntity<ResponseDto<TrainerMatchWaitingListResponseDto>> findMemberMatchWaitingList(
            @AuthenticationPrincipal Long userId
    )
    {
        ResponseDto<TrainerMatchWaitingListResponseDto> response = matchWaitingListService.findMemberMatchWaitingList(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping(ApiMappingPattern.MEMBER_MATCH_WAITING_LIST_API + CANCEL)
    public ResponseEntity<ResponseDto<Void>> matchCancel(
            @AuthenticationPrincipal Long userId
    ){

        ResponseDto<Void> response =  matchWaitingListService.matchCancel(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
