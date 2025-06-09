package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.CommentRequestDto;
import com.project.bcl_back.dto.board.response.CommentResponseDto;
import com.project.bcl_back.entity.Board;
import com.project.bcl_back.entity.Comment;
import com.project.bcl_back.repository.BoardRepository;
import com.project.bcl_back.repository.CommentRepository;
import com.project.bcl_back.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = false)
    public ResponseDto<CommentResponseDto> createComment(Long boardId, CommentRequestDto dto) {
        CommentResponseDto responseDto = null;

        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + boardId));

        Comment newComment = Comment.builder()
                .commentContent(dto.getCommentContent())
                .commenterId(dto.getCommenterId())
                .build();

        board.addComment(newComment);

        Comment savedComment = commentRepository.save(newComment);

        responseDto = CommentResponseDto.builder()
                .id(savedComment.getId())
                .postId(savedComment.getBoard().getId())
                .build();

        return ResponseDto.success(ResponseMessage.SUCCESS, "", responseDto);
    }

    @Override
    public ResponseDto<CommentResponseDto> updateComment(Long boardId, Long commentId, CommentRequestDto dto) {
        CommentResponseDto responseDto = null;

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_COMMENT + commentId));

        if(!comment.getBoard().getId().equals(boardId)){
            throw new IllegalArgumentException(ResponseMessage.COMMENT_NOT_BELONG_POST);
        }

        comment.setCommentContent(dto.getCommentContent());
        Comment updatedComment = commentRepository.save(comment);

        responseDto = CommentResponseDto.builder()
                .id(updatedComment.getId())
                .postId(updatedComment.getBoard().getId())
                .commentContent(updatedComment.getCommentContent())
                .commenterId(updatedComment.getCommenterId())
                .build();

        return ResponseDto.success(ResponseMessage.SUCCESS, "", responseDto);
    }

    @Override
    public ResponseDto<Void> deleteComment(Long boardId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_COMMENT + commentId));

        if (!comment.getBoard().getId().equals(boardId)){
            throw new IllegalArgumentException(ResponseMessage.COMMENT_NOT_BELONG_POST);
        }

        comment.getBoard().removeComment(comment);

        commentRepository.delete(comment);

        return ResponseDto.success(ResponseMessage.SUCCESS, "", null);
    }
}
