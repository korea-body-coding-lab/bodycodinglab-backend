package com.project.bcl_back.dto.user.response;

import com.project.bcl_back.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class GetMemberInfoResponseDto {
    private String username;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private String phone;
    private String email;
    private String memberAddress;
}
