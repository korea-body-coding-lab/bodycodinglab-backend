package com.project.bcl_back.controller;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.BoardRequestDto;
import com.project.bcl_back.dto.board.response.BoardResponseDto;
import com.project.bcl_back.service.BoardDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/personal-community-boards")
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


    // 게시글 단건 조회


    // 게시글 수정


    // 게시글 삭제
}
