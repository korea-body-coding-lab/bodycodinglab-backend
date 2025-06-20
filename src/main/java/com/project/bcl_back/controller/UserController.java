package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.request.DeleteUserRequestDto;
import com.project.bcl_back.dto.user.request.UpdateMemberInfoRequestDto;
import com.project.bcl_back.dto.user.request.UpdateTrainerInfoRequestDto;
import com.project.bcl_back.dto.user.response.DeleteUserResponseDto;
import com.project.bcl_back.dto.user.response.GetMemberInfoResponseDto;
import com.project.bcl_back.dto.user.response.GetTrainerInfoResponseDto;
<<<<<<< Updated upstream
import com.project.bcl_back.dto.user.response.GetUserInfoResponseDto;
=======
import com.project.bcl_back.entity.Match;
import com.project.bcl_back.service.MatchService;
>>>>>>> Stashed changes
import com.project.bcl_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(ApiMappingPattern.USER_API)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private static final String GET_USER_URL = "/me";
    private static final String MEMBER_MYPAGE_URL = "/members/me";
    private static final String TRAINER_MYPAGE_URL = "/trainers/me";
    private static final String DELETE_USER_URL = "/account-cancellation/me";
    private static final String UPDATE_PROFILE_URL = "/me/profile-image";

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_MYPAGE_URL)
    public ResponseEntity<ResponseDto<GetMemberInfoResponseDto>> getMemberInfo(@AuthenticationPrincipal Long id) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.getMemberInfo(id));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping(MEMBER_MYPAGE_URL + "/setting")
    public ResponseEntity<ResponseDto<GetMemberInfoResponseDto>> updateMemberInfo (
            @AuthenticationPrincipal Long id,
            @Valid @RequestBody UpdateMemberInfoRequestDto dto
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.updateMemberInfo(id, dto));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(TRAINER_MYPAGE_URL)
    public ResponseEntity<ResponseDto<GetTrainerInfoResponseDto>> getTrainerInfo(@AuthenticationPrincipal Long id) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.getTrainerInfo(id));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(TRAINER_MYPAGE_URL + "/setting")
    public ResponseEntity<ResponseDto<GetTrainerInfoResponseDto>> updateTrainerInfo (
            @AuthenticationPrincipal Long id,
            @Valid @RequestBody UpdateTrainerInfoRequestDto dto
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.updateTrainerInfo(id, dto));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @DeleteMapping(DELETE_USER_URL)
    public ResponseEntity<ResponseDto<DeleteUserResponseDto>> deleteUser (@AuthenticationPrincipal Long id, @Valid @RequestBody DeleteUserRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.deleteUser(id, dto));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @PutMapping(UPDATE_PROFILE_URL)
    public ResponseEntity<ResponseDto<Void>> updateProfileImage (
            @AuthenticationPrincipal Long id,
            @RequestPart(value = "profile") MultipartFile profile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.updateProfileImage(id, profile));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @DeleteMapping(value = UPDATE_PROFILE_URL)
    public ResponseEntity<ResponseDto<Void>> deleteProfileImage (@AuthenticationPrincipal Long id) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.deleteProfileImage(id));
    }
<<<<<<< Updated upstream

    @GetMapping(GET_USER_URL)
    public ResponseEntity<ResponseDto<GetUserInfoResponseDto>> getUserInformation (@AuthenticationPrincipal Long id) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.getUserInformation(id));
    }
=======
    @GetMapping("/{id}/username")
    public ResponseEntity<String> getUsername(@PathVariable Long id) {
        String username = userService.usernameFindById(id);
        return ResponseEntity.ok(username);
    }
    @PostMapping("/usernames")
    public ResponseEntity<Map<Long, String>> getUserNames(@RequestBody List<Long> userIds) {
        Map<Long, String> result = userService.getNamesByIds(userIds);
        return ResponseEntity.ok(result);
    }
    MatchService matchService;

    @GetMapping("/match-id")
    public ResponseEntity<?> getMyMatchId(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .orElse("");

        return matchService.findByUserId(userId, role)
                .map(match -> ResponseEntity.ok(Map.of("matchId", match.getId())))
                .orElse(ResponseEntity.ok(Map.of("matchId", null)));
    }

>>>>>>> Stashed changes
}
