package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.memberForm.request.MemberFormCreateRequestDto;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberFormService {
    ResponseDto<Void> createMemberForm(MemberFormCreateRequestDto dto);

    ResponseDto<MemberFormResponseDto> findByFromIdMemberForm(String formId);
}
