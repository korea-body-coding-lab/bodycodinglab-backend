package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.CommentRequestDto;
import com.project.bcl_back.dto.board.response.CommentResponseDto;
import com.project.bcl_back.service.CommentService;

public class CommentServiceImpl implements CommentService {
    @Override
    public ResponseDto<CommentResponseDto> createComment(Long boardId, CommentRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<CommentResponseDto> updateComment(Long boardId, Long commentId, CommentResponseDto dto) {
        return null;
    }

    @Override
    public ResponseDto<CommentResponseDto> deleteComment(Long boardId, Long commentId) {
        return null;
    }
}
