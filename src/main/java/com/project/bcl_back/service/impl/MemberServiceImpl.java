package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.user.response.GetMemberNameResponseDto;
import com.project.bcl_back.entity.Member;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.MemberRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    @Override
    public ResponseDto<GetMemberNameResponseDto> findMemberByNameAndUsername(String username, String name) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        if (!user.getName().equals(name)) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, "이름과 아이디가 일치하지 않습니다.");
        }

        Member member = memberRepository.findByUserId(user.getId()).orElse(null);
        if (member == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        GetMemberNameResponseDto dto = GetMemberNameResponseDto.builder()
                .memberId(member.getMemberId())
                .username(user.getUsername())
                .name(user.getName())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, dto);
    }
}
