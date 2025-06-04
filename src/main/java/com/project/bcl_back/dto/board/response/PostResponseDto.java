package com.project.bcl_back.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long writerId;
    private String title;
    private String content;
    private String createdAt;
}
