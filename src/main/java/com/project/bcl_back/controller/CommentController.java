package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.CommentRequestDto;
import com.project.bcl_back.dto.board.response.CommentResponseDto;
import com.project.bcl_back.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BOARD_COMMENT_API)
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 1.댓글 생성
    @PostMapping
    public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
            @PathVariable Long boardId,
            @Valid @RequestBody CommentRequestDto dto
    ) {
        ResponseDto<CommentResponseDto> responseDto = commentService.createComment(boardId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 2.댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto dto
    ){
        ResponseDto<CommentResponseDto> response = commentService.updateComment(boardId, commentId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 3.댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ){
        ResponseDto<Void> response = commentService.deleteComment(boardId, commentId);
        return ResponseEntity.noContent().build();
    }
    // 4.댓글 목록 조회
    @GetMapping("")
    public ResponseEntity<ResponseDto<List<CommentResponseDto>>> getComments(
            @PathVariable Long boardId
    ) {
        ResponseDto<List<CommentResponseDto>> response = commentService.getComments(boardId);
        return ResponseEntity.ok(response);
    }
}
