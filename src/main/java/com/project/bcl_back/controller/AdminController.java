package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerDetailResponseDto;
import com.project.bcl_back.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API)
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    private static final String GET_TRAINERS = "/trainers";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_TRAINERS)
    public ResponseEntity<ResponseDto<Page<GetAllTrainersResponseDto>>> getAllTrainers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TrainerStatus status
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.getAllTrainers(page, size, status));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_TRAINERS + "/{trainerId}")
    public ResponseEntity<ResponseDto<GetTrainerDetailResponseDto>> getTrainer(@PathVariable Long trainerId) {
        return ResponseDto.toResponseEntity(HttpStatus.OK,adminService.getTrainer(trainerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(GET_TRAINERS + "/{trainerId}")
    public Mono<ResponseEntity<ResponseDto<GetTrainerDetailResponseDto>>> updateTrainerStatus(
            @AuthenticationPrincipal Long id,
            @PathVariable Long trainerId,
            @Valid @RequestBody UpdateTrainerStatusRequestDto dto
    ) {
        return adminService.updateTrainerStatus(id, trainerId, dto);
    }

}
