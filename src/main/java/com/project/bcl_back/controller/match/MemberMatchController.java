package com.project.bcl_back.controller.match;


import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.match.response.TrainerMatchResponseDto;
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

@RestController
@RequiredArgsConstructor
public class MemberMatchController {
    private final MatchService matchService;

    private static final String SELECT_MATCH = "/{matchId}";


    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(ApiMappingPattern.MEMER_MATCH_API)
    public ResponseEntity<ResponseDto<TrainerMatchResponseDto>> findMemberMatch(
            @AuthenticationPrincipal Long userId
    ){
        ResponseDto<TrainerMatchResponseDto> response = matchService.findMemberMatch(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping(ApiMappingPattern.MEMER_MATCH_API + SELECT_MATCH)
    public ResponseEntity<ResponseDto<Void>> cancelMatch(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long matchId
    ) {
        ResponseDto<Void> response = matchService.cancelMatch(userId, matchId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
