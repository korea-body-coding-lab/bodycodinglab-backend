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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberFormController {
    private final MemberFormService memberFormService;

    private static final String POST_MEMBER_FORMS = "/api/v1/members/subscriptions/forms";
    private static final String GET_MEMBER_FORMS = "/api/v1/trainers/me/match-waiting-list/{formId}";

    @PostMapping(POST_MEMBER_FORMS)
    public ResponseEntity<ResponseDto<Void>> createMemberForm(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody MemberFormCreateRequestDto dto
    ){
        Long memberId = user.getId();
        ResponseDto<Void> response = memberFormService.createMemberForm(memberId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(GET_MEMBER_FORMS)
    public ResponseEntity<ResponseDto<MemberFormResponseDto>> findByFormIdMemberForm(@PathVariable Long formId){
        ResponseDto<MemberFormResponseDto> response = memberFormService.findByFromIdMemberForm(formId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
