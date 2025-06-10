package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.dto.admin.response.GetTrainerResponseDto;
import com.project.bcl_back.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API)
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    private static final String GET_TRAINERS = "/trainers";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_TRAINERS)
    public ResponseEntity<ResponseDto<List<GetAllTrainersResponseDto>>> getAllTrainers() {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.getAllTrainers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_TRAINERS + "/{trainerId}")
    public ResponseEntity<ResponseDto<GetTrainerResponseDto>> getTrainer(@PathVariable Long trainerId) {
        return ResponseDto.toResponseEntity(HttpStatus.OK,adminService.getTrainer(trainerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(GET_TRAINERS + "/{trainerId}")
    public ResponseEntity<ResponseDto<GetTrainerResponseDto>> updateTrainerStatus(
            @AuthenticationPrincipal Long id,
            @PathVariable Long trainerId,
            @Valid @RequestBody UpdateTrainerStatusRequestDto dto
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.updateTrainerStatus(id, trainerId, dto));
    }

}
