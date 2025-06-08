package com.project.bcl_back.service.impl;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.note.request.NoteRequestDto;
import com.project.bcl_back.dto.note.response.NoteResponseDto;
import com.project.bcl_back.service.NoteService;

import java.util.List;

public class NoteServiceImpl implements NoteService {
    @Override
    public ResponseDto<NoteResponseDto> createNote(Long noteId, NoteRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<List<NoteResponseDto>> getAllNotes() {
        return null;
    }

    @Override
    public ResponseDto<NoteResponseDto> getNoteById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<?> deleteNote(Long id) {
        return null;
    }
}
