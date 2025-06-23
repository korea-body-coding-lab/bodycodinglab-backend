package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.response.GetMemberNameResponseDto;
import com.project.bcl_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private static final String GET_MEMBER_NAME = ApiMappingPattern.MEMBER_API+ "/username-and-name";

    @GetMapping(GET_MEMBER_NAME)
    public ResponseEntity<ResponseDto<GetMemberNameResponseDto>> findMemberByNameAndUsername(
            @RequestParam String username,
            @RequestParam String name) {
        ResponseDto<GetMemberNameResponseDto> response = memberService.findMemberByNameAndUsername(username, name);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}

