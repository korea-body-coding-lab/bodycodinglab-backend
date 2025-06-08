package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.note.request.NoteRequestDto;
import com.project.bcl_back.dto.note.response.NoteResponseDto;
import com.project.bcl_back.entity.Note;
import com.project.bcl_back.repository.NoteRepository;
import com.project.bcl_back.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepo;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public ResponseDto<NoteResponseDto> createNote(NoteRequestDto dto) {
        NoteResponseDto responseDto = null;
        Note note = Note.builder()
                .noteText(dto.getNoteText())
                .noteReceiver(dto.getNoteReceiver())
                .build();
        note = noteRepo.save(note);

        NoteResponseDto data = toDto(note);
        return ResponseDto.success(ResponseMessage.SUCCESS, "", data);
    }

    @Override
    public ResponseDto<List<NoteResponseDto>> getAllNotes() {
        List<NoteResponseDto> list = noteRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseMessage.SUCCESS, "", list);
    }

    @Override
    public ResponseDto<NoteResponseDto> getNoteById(Long id) {
        Note note = noteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("쪽지를 찾을 수 없습니다."));
        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(note));
    }

    @Override
    public ResponseDto<?> deleteNote(Long id) {
        noteRepo.deleteById(id);
        return ResponseDto.success(ResponseMessage.SUCCESS, null);

    }

    private NoteResponseDto toDto(Note note){
        return NoteResponseDto.builder()
                .noteText(note.getNoteText())
                .noteWriter(note.getNoteWriter())
                .noteReceiver(note.getNoteReceiver())
                .noteCreateTime(note.getNoteCreateTime().format(FORMAT))
                .build();
    }
}
