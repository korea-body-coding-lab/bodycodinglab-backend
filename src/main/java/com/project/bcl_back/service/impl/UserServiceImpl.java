package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.auth.request.SignInUserRequestDto;
import com.project.bcl_back.dto.auth.request.SignUpMemberRequestDto;
import com.project.bcl_back.dto.auth.response.SignInUserResponseDto;
import com.project.bcl_back.dto.auth.response.SignUpMemberResponseDto;
import com.project.bcl_back.repository.RoleRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseDto<SignUpMemberResponseDto> memberSignup(SignUpMemberRequestDto dto) {
        SignUpMemberResponseDto data = null;

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessage.DUPLICATED_USER_ID);
        }

        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessage.DUPLICATED_TEL_NUMBER);
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessage.NOT_MATCH_PASSWORD);
        }

        return null;
    }

    @Override
    public ResponseDto<SignInUserResponseDto> login(SignInUserRequestDto dto) {
        return null;
    }
}