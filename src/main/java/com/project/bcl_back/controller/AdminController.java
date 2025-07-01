package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerDetailResponseDto;
import com.project.bcl_back.service.AdminService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API)
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    private static final String GET_ALL_TRAINERS = "/trainers";
    private static final String GET_TRAINER_DETAIL = GET_ALL_TRAINERS + "/{trainerId}";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_ALL_TRAINERS)
    public ResponseEntity<ResponseDto<Page<GetAllTrainersResponseDto>>> getAllTrainers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TrainerStatus status
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.getAllTrainers(page, size, status));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_TRAINER_DETAIL)
    public ResponseEntity<ResponseDto<GetTrainerDetailResponseDto>> getTrainerDetail(@PathVariable Long trainerId) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.getTrainerDetail(trainerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(GET_TRAINER_DETAIL)
    public ResponseEntity<ResponseDto<GetTrainerDetailResponseDto>> updateTrainerStatus(
            @AuthenticationPrincipal Long id,
            @PathVariable Long trainerId,
            @Valid @RequestBody UpdateTrainerStatusRequestDto dto
    ) throws MessagingException {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.updateTrainerStatus(id, trainerId, dto));
    }

}
