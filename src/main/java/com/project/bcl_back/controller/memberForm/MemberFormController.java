package com.project.bcl_back.controller.memberForm;


import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.memberForm.request.MemberFormCreateRequestDto;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
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


    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping(ApiMappingPattern.MEMBER_FORM_API)
    public ResponseEntity<ResponseDto<Long>> createMemberForm(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody MemberFormCreateRequestDto dto
    ){
        ResponseDto<Long> response = memberFormService.createMemberForm(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(ApiMappingPattern.MEMBER_FORM_API + "/find")
    public ResponseEntity<ResponseDto<MemberFormResponseDto>> findByFormIdMemberForm(@AuthenticationPrincipal Long userId){
        ResponseDto<MemberFormResponseDto> response = memberFormService.findByFromIdMemberForm(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
