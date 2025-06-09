package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API)
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    private static final String GET_TRAINER_LIST = "/trainers";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_TRAINER_LIST)
    public ResponseEntity<ResponseDto<List<GetAllTrainersResponseDto>>> getAllTrainers() {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.getAllTrainers());
    }

}
