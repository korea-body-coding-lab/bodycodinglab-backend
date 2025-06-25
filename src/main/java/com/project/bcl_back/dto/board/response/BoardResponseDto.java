package com.project.bcl_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private Long writerId;
    private String writerName;
    private String title;
    private String content;
    private String createdAt;
    private String profileImageUrl;
}
