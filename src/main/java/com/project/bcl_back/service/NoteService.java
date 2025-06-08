package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.note.request.NoteRequestDto;
import com.project.bcl_back.dto.note.response.NoteResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface NoteService {
    // 쪽지 작성
    ResponseDto<NoteResponseDto> createNote(@Valid NoteRequestDto dto);

    // 쪽지 전체 조회
    ResponseDto<List<NoteResponseDto>> getAllNotes();

    // 쪽지 단건 조회
    ResponseDto<NoteResponseDto> getNoteById(Long id);

    // 쪽지 삭제
    ResponseDto<?> deleteNote(Long id);
}
