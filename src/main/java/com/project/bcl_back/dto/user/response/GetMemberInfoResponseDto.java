package com.project.bcl_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class GetMemberInfoResponseDto {
    private String username;
    private String name;
    private LocalDate birthdate;
    private String gender;
    private String phone;
    private String email;
    private String memberAddress;
    private String profileImageUrl;
}
