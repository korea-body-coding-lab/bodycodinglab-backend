package com.project.bcl_back.dto.admin.response;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class GetTrainerDetailResponseDto {
    private Long userId;
    private Long trainerId;
    private String username;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private String phone;
    private String email;
    private String jobAddress;
    private String createdAt;
    private TrainerStatus status;
//    private String attachmentFileUrl;
//    private String profileImageUrl;
}
