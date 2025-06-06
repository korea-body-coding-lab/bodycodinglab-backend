package com.project.bcl_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class GetTrainerInfoResponseDto {
    private String username;
    private String name;
    private LocalDate birthdate;
    private String gender;
    private String phone;
    private String email;
    private String jobAddress;
    private String attachmentFileUrl;
    private String profileImageUrl;
}
