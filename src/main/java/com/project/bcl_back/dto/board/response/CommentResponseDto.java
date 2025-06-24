package com.project.bcl_back.dto.board.response;

import com.project.bcl_back.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private Long postId;

    private final String commentContent;
    private final Long commenterId;
    private String createdAt;
}
