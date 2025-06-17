package com.project.bcl_back.controller;


import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.memberForm.request.MemberFormCreateRequestDto;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.service.MemberFormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberFormController {
    private final MemberFormService memberFormService;

    private static final String MEMBER_FORMS = "/api/v1/users/members/me/forms";


    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping(MEMBER_FORMS)
    public ResponseEntity<ResponseDto<Long>> createMemberForm(
            @AuthenticationPrincipal Long memberId,
            @Valid @RequestBody MemberFormCreateRequestDto dto
    ){
        ResponseDto<Long> response = memberFormService.createMemberForm(memberId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_FORMS )
    public ResponseEntity<ResponseDto<MemberFormResponseDto>> findByFormIdMemberForm(@AuthenticationPrincipal Long memberId){
        ResponseDto<MemberFormResponseDto> response = memberFormService.findByFromIdMemberForm(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
