package com.project.bcl_back.dto.matchWatingList.response;

import com.project.bcl_back.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberMatchWaitingListResponseDto {
    private Long matchWaitingListId;
    private Long memberId;
    private String memberName;
    private int memberAge;
    private Gender memberGender;
    private LocalDateTime appliedAt;
}
