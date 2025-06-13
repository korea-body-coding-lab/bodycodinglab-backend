package com.project.bcl_back.dto.matchWatingList.request;

import com.project.bcl_back.common.enums.matchWaitingList.ApprovedStatus;
import lombok.Getter;

@Getter
public class MatchRejectRequestDto {
    private ApprovedStatus approvedStatus;

}
