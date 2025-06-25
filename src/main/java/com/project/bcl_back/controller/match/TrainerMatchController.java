package com.project.bcl_back.controller.match;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchListResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchResponseDto;
import com.project.bcl_back.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainerMatchController {
    private final MatchService matchService;


    private static final String SELECT_MATCH = "/{matchId}";

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(ApiMappingPattern.TRAINER_MATCH_API)
    public ResponseEntity<ResponseDto<List<MemberMatchListResponseDto>>> findMatchTrainerList(
            @AuthenticationPrincipal Long userId
    ){

        ResponseDto<List<MemberMatchListResponseDto>> response = matchService.findMatchTrainerList(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(ApiMappingPattern.TRAINER_MATCH_API + SELECT_MATCH)
    public ResponseEntity<ResponseDto<MemberMatchResponseDto>> findMatchTrainer(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long matchId
    ){
        ResponseDto<MemberMatchResponseDto> response = matchService.findMatchTrainer(userId, matchId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
