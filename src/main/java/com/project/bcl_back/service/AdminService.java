package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;

import java.util.List;

public interface AdminService {
    ResponseDto<List<GetAllTrainersResponseDto>> getAllTrainers();
}
