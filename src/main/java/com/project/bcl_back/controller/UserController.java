package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.request.DeleteUserRequestDto;
import com.project.bcl_back.dto.user.request.UpdateMemberInfoRequestDto;
import com.project.bcl_back.dto.user.request.UpdateTrainerInfoRequestDto;
import com.project.bcl_back.dto.user.response.DeleteUserResponseDto;
import com.project.bcl_back.dto.user.response.GetMemberInfoResponseDto;
import com.project.bcl_back.dto.user.response.GetTrainerInfoResponseDto;
import com.project.bcl_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ApiMappingPattern.USER_API)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private static final String MEMBER_MYPAGE_URL = "/members/me";
    private static final String TRAINER_MYPAGE_URL = "/trainers/me";
    private static final String DELETE_USER_URL = "/account-cancellation/me";

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_MYPAGE_URL)
    public ResponseEntity<ResponseDto<GetMemberInfoResponseDto>> getMemberInfo(@AuthenticationPrincipal Long id) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.getMemberInfo(id));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping(MEMBER_MYPAGE_URL + "/setting")
    public ResponseEntity<ResponseDto<GetMemberInfoResponseDto>> updateMemberInfo (
            @AuthenticationPrincipal Long id,
            @Valid @RequestPart(value = "dto") UpdateMemberInfoRequestDto dto,
            @RequestPart(value = "profile", required = false) MultipartFile profile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.updateMemberInfo(id, dto, profile));
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
            @Valid @RequestPart(value = "dto") UpdateTrainerInfoRequestDto dto,
            @RequestPart(value = "profile", required = false) MultipartFile profile
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.updateTrainerInfo(id, dto, profile));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @DeleteMapping(DELETE_USER_URL)
    public ResponseEntity<ResponseDto<DeleteUserResponseDto>> deleteUser (@AuthenticationPrincipal Long id, @Valid @RequestBody DeleteUserRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.deleteUser(id, dto));
    }
}
