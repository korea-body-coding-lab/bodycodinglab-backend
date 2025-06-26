package com.project.bcl_back.dto.board.response;

import com.project.bcl_back.dto.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailResponseDto {
    private Long id;
    private Long writerId;
    private String title;
    private String content;
    private List<CommentResponseDto> comments;
    private Long matchId;

    private List<FileResponseDto> images;
}
