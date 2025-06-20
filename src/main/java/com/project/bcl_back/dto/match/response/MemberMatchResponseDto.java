package com.project.bcl_back.dto.match.response;

import com.project.bcl_back.common.enums.user.Gender;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberMatchResponseDto {
    private String profileImageUrl;
    private String memberName;
    private int memberAge;
    private Gender memberGender;
    private String memberPhone;
    private String memberAddress;
    private MemberFormResponseDto memberFormResponseDto;

    public MemberMatchResponseDto(
            String profileImageUrl,
            String memberName,
            int memberAge,
            Gender memberGender,
            String memberPhone,
            String memberAddress){
        this.profileImageUrl = profileImageUrl;
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.memberGender = memberGender;
        this.memberPhone = memberPhone;
        this.memberAddress = memberAddress;
    }
}
