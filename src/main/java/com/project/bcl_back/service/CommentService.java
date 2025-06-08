package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.CommentRequestDto;
import com.project.bcl_back.dto.board.response.CommentResponseDto;
import jakarta.validation.Valid;

public interface CommentService {
    // 댓글 작성
    ResponseDto<CommentResponseDto> createComment(Long boardId, @Valid CommentRequestDto dto);

    // 댓글 수정
    ResponseDto<CommentResponseDto> updateComment(Long boardId, Long commentId ,CommentResponseDto dto);

    // 댓글 삭제
    ResponseDto<CommentResponseDto> deleteComment(Long boardId, Long commentId);
}
