package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.note.request.NoteRequestDto;
import com.project.bcl_back.dto.note.response.NoteResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteService {
    // 쪽지 작성
    ResponseDto<NoteResponseDto> createNote(@Valid NoteRequestDto dto, Long writerId);

    // 쪽지 전체 조회

    // 쪽지 단건 조회
    ResponseDto<NoteResponseDto> getNoteById(Long id);

    // 쪽지 삭제
    ResponseDto<?> deleteNote(Long id);

    // 쪽지 분류
    ResponseDto<Page<NoteResponseDto>> getReceivedNotes(Long userId, Pageable pageable);
    ResponseDto<Page<NoteResponseDto>> getSentNotes(Long userId, Pageable pageable);

    ResponseDto<Page<NoteResponseDto>> findByNoteWriterOrNoteReceiver(Long userId, Pageable pageable);
}
