package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.BoardRequestDto;
import com.project.bcl_back.dto.board.response.BoardResponseDto;
import com.project.bcl_back.service.BoardDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BOARD_API)
@RequiredArgsConstructor
public class BoardController {
    private final BoardDataService boardDataService;
    // 게시글 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<BoardResponseDto>> createPost(
            @RequestPart("data") @Valid BoardRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    )throws IOException {
        ResponseDto<BoardResponseDto> response = boardDataService.createPost(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<BoardResponseDto>>> getAllPosts(){
        return ResponseEntity.ok(boardDataService.getAllPosts());
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(boardDataService.getPostById(id));
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> updatePost(
            @PathVariable Long id,
            @RequestPart("data") @Valid BoardRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    )throws IOException {
        return ResponseEntity.ok(boardDataService.updatePost(id, dto, file));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(boardDataService.deletePost(id));
    }
}
