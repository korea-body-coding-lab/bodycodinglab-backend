package com.project.bcl_back.dto.user.response;

import com.project.bcl_back.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetMemberNameResponseDto {
    private Long memberId;
    private String username;
    private String name;

    public GetMemberNameResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.username = member.getUser().getUsername();
        this.name = member.getUser().getName();
    }
}
