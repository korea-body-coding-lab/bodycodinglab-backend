package com.project.bcl_back.dto.matchWatingList.response;

import com.project.bcl_back.common.enums.matchWaitingList.ApprovedStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrainerMatchWaitingListResponseDto {
    private Long matchWaitingListId;
    private Long trainerId;
    private String profileImageUrl;
    private String trainerName;
    private String trainerJobAddress;
    private LocalDateTime appliedAt;
    private ApprovedStatus approvedStatus;
    private String rejectResponse;
}
