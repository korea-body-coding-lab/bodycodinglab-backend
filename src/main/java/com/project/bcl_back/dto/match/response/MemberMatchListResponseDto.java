package com.project.bcl_back.dto.match.response;

import com.project.bcl_back.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberMatchListResponseDto {
    private String memberName;
    private int memberAge;
    private Gender memberGender;
}
