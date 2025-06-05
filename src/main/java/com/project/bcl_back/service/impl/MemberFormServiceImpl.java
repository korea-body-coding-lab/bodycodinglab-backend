package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.memberForm.request.MemberFormCreateRequestDto;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
import com.project.bcl_back.service.MemberFormService;
import org.springframework.stereotype.Service;

@Service
public class MemberFormServiceImpl implements MemberFormService {
    @Override
    public ResponseDto<Void> createMemberForm(MemberFormCreateRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<MemberFormResponseDto> findByFromIdMemberForm(String formId) {
        return null;
    }
}
