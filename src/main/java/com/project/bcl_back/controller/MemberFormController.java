package com.project.bcl_back.controller;


import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.memberForm.request.MemberFormCreateRequestDto;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
import com.project.bcl_back.service.MemberFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberFormController {
    private MemberFormService memberFormService;

    private static final String POST_MEMBER_FORMS = "api/v1/members/subscriptions/forms";
    private static final String GET_MEMBER_FORMS = "api/v1/trainers/me/match-waiting-list/{form-id}";

    @PostMapping(POST_MEMBER_FORMS)
    public ResponseEntity<ResponseDto<Void>> createMemberForm(
            @PathVariable Long memberId,
            @RequestBody MemberFormCreateRequestDto dto
    ){
        ResponseDto<Void> response = memberFormService.createMemberForm(memberId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(GET_MEMBER_FORMS)
    public ResponseEntity<ResponseDto<MemberFormResponseDto>> findByFormIdMemberForm(@PathVariable Long formId){
        ResponseDto<MemberFormResponseDto> response = memberFormService.findByFromIdMemberForm(formId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
