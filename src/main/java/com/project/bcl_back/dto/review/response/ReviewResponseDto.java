package com.project.bcl_back.dto.review.response;

import java.time.LocalDateTime;

public class ReviewResponseDto {
    // 회원 프로필 이미지
    private String memberName;
    private String content;
    // 리뷰 이미지
    private LocalDateTime createAt;
    private Short reviewScore;
    private Integer recommandCount;
}
