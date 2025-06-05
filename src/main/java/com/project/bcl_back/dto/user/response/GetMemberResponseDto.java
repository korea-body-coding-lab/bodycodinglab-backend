package com.project.bcl_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class GetMemberResponseDto {
    private String username;
    private String name;
    private Date birthdate;
    private String gender;
    private String phone;
    private String email;
    private String memberAddress;
    private String profileImageUrl;
}
