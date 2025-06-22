package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.response.GetMemberNameResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    ResponseDto<GetMemberNameResponseDto> findMemberByNameAndUsername(String username, String name);
}
