package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.request.DeleteUserRequestDto;
import com.project.bcl_back.dto.user.request.UpdateMemberInfoRequestDto;
import com.project.bcl_back.dto.user.request.UpdateTrainerInfoRequestDto;
import com.project.bcl_back.dto.user.response.DeleteUserResponseDto;
import com.project.bcl_back.dto.user.response.GetMemberInfoResponseDto;
import com.project.bcl_back.dto.user.response.GetTrainerInfoResponseDto;
import com.project.bcl_back.dto.user.response.GetUserInfoResponseDto;
import com.project.bcl_back.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    ResponseDto<GetMemberInfoResponseDto> getMemberInfo(Long id);
    ResponseDto<GetMemberInfoResponseDto> updateMemberInfo(Long id, @Valid UpdateMemberInfoRequestDto dto) throws IOException;
    ResponseDto<GetTrainerInfoResponseDto> getTrainerInfo(Long id);
    ResponseDto<GetTrainerInfoResponseDto> updateTrainerInfo(Long id, @Valid UpdateTrainerInfoRequestDto dto) throws IOException;
    ResponseDto<DeleteUserResponseDto> deleteUser(Long id, @Valid DeleteUserRequestDto dto);
    ResponseDto<Void> updateProfileImage(Long id, MultipartFile profile) throws IOException;
    ResponseDto<Void> deleteProfileImage(Long id) throws IOException;
    ResponseDto<GetUserInfoResponseDto> getUserInformation(Long id);
    User findById(Long id);
}
