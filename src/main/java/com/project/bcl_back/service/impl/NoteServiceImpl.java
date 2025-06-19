package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.note.request.NoteRequestDto;
import com.project.bcl_back.dto.note.response.NoteResponseDto;
import com.project.bcl_back.entity.Note;
import com.project.bcl_back.repository.NoteRepository;
import com.project.bcl_back.service.NoteService;
import com.project.bcl_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepo;
    private final UserService userService;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public ResponseDto<NoteResponseDto> createNote(NoteRequestDto dto) {
        // 유효한 사용자 검증
        userService.findById(dto.getNoteWriter());
        userService.findById(dto.getNoteReceiver());

        Note note = Note.builder()
                .noteText(dto.getNoteText())
                .noteWriter(dto.getNoteWriter())
                .noteReceiver(dto.getNoteReceiver())
                .noteCreateTime(LocalDateTime.now())
                .build();

        note = noteRepo.save(note);
        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(note));
    }

    @Override
    public ResponseDto<List<NoteResponseDto>> getAllNotes() {
        List<NoteResponseDto> list = noteRepo.findAll().stream()
                .map(this::toDto)
                .toList();
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

    @Override
    public ResponseDto<List<NoteResponseDto>> getReceivedNotes(Long userId) {
        userService.findById(userId); // 존재 확인
        return ResponseDto.success(ResponseMessage.SUCCESS, "",
                noteRepo.findByNoteReceiver(userId).stream()
                        .map(this::toDto)
                        .toList()
        );
    }

    @Override
    public ResponseDto<List<NoteResponseDto>> getSentNotes(Long userId) {
        userService.findById(userId);
        return ResponseDto.success(ResponseMessage.SUCCESS, "",
                noteRepo.findByNoteWriter(userId).stream()
                        .map(this::toDto)
                        .toList()
        );
    }

    private NoteResponseDto toDto(Note note){
        return NoteResponseDto.builder()
                .id(note.getId())
                .noteText(note.getNoteText())
                .noteWriter(note.getNoteWriter())
                .noteReceiver(note.getNoteReceiver())
                .noteCreateTime(note.getNoteCreateTime().format(FORMAT))
                .build();
    }
}
