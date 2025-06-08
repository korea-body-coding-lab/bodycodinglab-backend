package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.BoardRequestDto;
import com.project.bcl_back.dto.board.response.BoardResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardDataService {
    // 게시글 등록
    ResponseDto<BoardResponseDto> createPost(BoardRequestDto dto, MultipartFile file)throws IOException;

    // 전체 게시글 조회
    ResponseDto<List<BoardResponseDto>> getAllPosts();

    // 단건 게시글 조회
    ResponseDto<BoardResponseDto> getPostById(Long id);

    // 게시글 수정
    ResponseDto<BoardResponseDto> updatePost(Long id, BoardRequestDto dto, MultipartFile file) throws IOException;

    // 게시글 삭제
    ResponseDto<?> deletePost(Long id);

}
