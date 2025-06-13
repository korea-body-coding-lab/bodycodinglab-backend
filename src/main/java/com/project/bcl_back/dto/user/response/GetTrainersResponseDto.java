package com.project.bcl_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetTrainersResponseDto {
    private String username;
    private String name;
    private int age;
    private String gender;
    private String trainerJobAddress;
    private String status;
}
