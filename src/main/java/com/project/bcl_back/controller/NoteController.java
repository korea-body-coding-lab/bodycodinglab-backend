package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.UserPrincipal;
import com.project.bcl_back.dto.note.request.NoteRequestDto;
import com.project.bcl_back.dto.note.response.NoteResponseDto;
import com.project.bcl_back.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_API)
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    // 1. 노트 작성
    @PostMapping
    public ResponseEntity<ResponseDto<NoteResponseDto>> createNote(
            @Valid @RequestBody NoteRequestDto dto
    ) {
        ResponseDto<NoteResponseDto> response = noteService.createNote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. 노트 전체 조회
    @GetMapping("/allnotes")
    public ResponseEntity<ResponseDto<List<NoteResponseDto>>> findByNoteWriterOrNoteReceiver(
            @AuthenticationPrincipal Long userId
    ){
        return ResponseEntity.ok(noteService.findByNoteWriterOrNoteReceiver(userId));
    }
    // 3. 노트 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<NoteResponseDto>> getNoteById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(noteService.getNoteById(id));
    }
    // 4. 노트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deleteNoteById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(noteService.deleteNote(id));
    }
    @GetMapping("/received")
    public ResponseEntity<ResponseDto<List<NoteResponseDto>>> getReceivedNotes(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(noteService.getReceivedNotes(userId));
    }

    @GetMapping("/sent")
    public ResponseEntity<ResponseDto<List<NoteResponseDto>>> getSentNotes(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(noteService.getSentNotes(userId));
    }

}
