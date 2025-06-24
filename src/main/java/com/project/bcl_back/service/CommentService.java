package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.CommentRequestDto;
import com.project.bcl_back.dto.board.response.CommentResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CommentService {
    // 댓글 작성
    ResponseDto<CommentResponseDto> createComment(Long boardId, @Valid CommentRequestDto dto);

    // 댓글 수정
    ResponseDto<CommentResponseDto> updateComment(Long boardId, Long commentId ,CommentRequestDto dto);

    // 댓글 삭제
    ResponseDto<Void> deleteComment(Long boardId, Long commentId);

    // 댓글 조회
    ResponseDto<List<CommentResponseDto>> getComments(Long boardId);
}
