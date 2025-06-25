package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.BoardRequestDto;
import com.project.bcl_back.dto.board.response.BoardResponseDto;
import com.project.bcl_back.provider.JwtProvider;
import com.project.bcl_back.repository.MatchRepository;
import com.project.bcl_back.service.BoardDataService;
import com.project.bcl_back.service.MatchService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final MatchService matchService;
    private final JwtProvider jwtProvider;

    // 게시글 등록
    @PostMapping(value="/{categoryId}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<BoardResponseDto>> createPost(
            @PathVariable Long matchId,
            @PathVariable Long categoryId,
            @RequestPart("data") @Valid BoardRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request
    )throws IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        Long userId = jwtProvider.getUserIdFromJwt(token);
        if (!matchService.isUserInMatch(userId, matchId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ResponseDto<BoardResponseDto> response = boardDataService.createPost(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 목록 조회
    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseDto<List<BoardResponseDto>>> getPostByCategory(
            @PathVariable Long matchId,
            @PathVariable("categoryId") int categoryId,
            HttpServletRequest request
    ){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        Long userId = jwtProvider.getUserIdFromJwt(token);
        if (!matchService.isUserInMatch(userId, matchId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ResponseDto<List<BoardResponseDto>> posts = boardDataService.getPostByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    // 게시글 단건 조회
    @GetMapping("/{categoryId}/{id}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> getPost(
            @PathVariable Long matchId,
            @PathVariable("categoryId") int categoryId,
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        Long userId = jwtProvider.getUserIdFromJwt(token);
        if (!matchService.isUserInMatch(userId, matchId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(boardDataService.getPostById(id));
    }

    // 게시글 수정
    @PutMapping("/{categoryId}/{id}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> updatePost(
            @PathVariable Long matchId,
            @PathVariable Long id,
            @PathVariable("categoryId") int categoryId,
            @RequestPart("data") @Valid BoardRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request
    )throws IOException {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtProvider.getUserIdFromJwt(token);

        if (!boardDataService.isPostWriter(userId, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(boardDataService.updatePost(id, dto, file));
    }

    // 게시글 삭제
    @DeleteMapping("/{categoryId}/{id}")
    public ResponseEntity<ResponseDto<?>> deletePost(
            @PathVariable Long matchId,
            @PathVariable Long id,
            @PathVariable("categoryId") int categoryId,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtProvider.getUserIdFromJwt(token);

        if (!boardDataService.isPostWriter(userId, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(boardDataService.deletePost(id));
    }
}
