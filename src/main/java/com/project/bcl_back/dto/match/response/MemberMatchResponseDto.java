package com.project.bcl_back.dto.match.response;

import com.project.bcl_back.common.enums.user.Gender;
import com.project.bcl_back.entity.MemberForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberMatchResponseDto {
    private String memberName;
    private int memberAge;
    private Gender memberGender;
    private String memberPhone;
    private String memberAddress;
    private MemberForm memberForm;

    public MemberMatchResponseDto(
            String memberName,
            int memberAge,
            Gender memberGender,
            String memberPhone,
            String memberAddress){
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.memberGender = memberGender;
        this.memberPhone = memberPhone;
        this.memberAddress = memberAddress;
    }
}
