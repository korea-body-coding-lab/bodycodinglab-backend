package com.project.bcl_back.dto.admin.response;

import com.project.bcl_back.common.enums.trainerInfo.Status;
import com.project.bcl_back.common.enums.user.Gender;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class GetTrainerResponseDto {
    private Long userId;
    private Long trainerId;
    private String username;
    private String name;
    private LocalDate birthdate;
    private int age;
    private Gender gender;
    private String phone;
    private String email;
    private String jobAddress;
    private String createdAt;
    private Status status;
//    private String attachmentFileUrl;
//    private String profileImageUrl;
}
