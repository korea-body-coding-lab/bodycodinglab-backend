package com.project.bcl_back.dto.admin.response;

import com.project.bcl_back.common.enums.trainerInfo.Status;
import com.project.bcl_back.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
@Builder
public class GetAllTrainersResponseDto {
    private Long userId;
    private Long trainerId;
    private String username;
    private String name;
    private int age;
    private Gender gender;
    private String createAt;
    private Status status;
}
