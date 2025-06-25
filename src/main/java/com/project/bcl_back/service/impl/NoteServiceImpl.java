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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseDto<NoteResponseDto> createNote(NoteRequestDto dto, Long writerId) {
        // 유효한 사용자 검증
        userService.findById(writerId);
        userService.findById(dto.getNoteReceiver());

        Note note = Note.builder()
                .noteText(dto.getNoteText())
                .noteWriter(writerId)
                .noteReceiver(dto.getNoteReceiver())
                .noteCreateTime(LocalDateTime.now())
                .build();

        note = noteRepo.save(note);
        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(note));
    }

    @Override
    public ResponseDto<Page<NoteResponseDto>> findByNoteWriterOrNoteReceiver(Long userId, Pageable pageable) {
        userService.findById(userId);
        Page<Note> notePage = noteRepo.findByNoteWriterOrNoteReceiver(userId, userId, pageable);

        Page<NoteResponseDto> page = notePage.map(this::toDto);
        return ResponseDto.success(ResponseMessage.SUCCESS, "", page);
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
    public ResponseDto<Page<NoteResponseDto>> getReceivedNotes(Long userId, Pageable pageable) {
        userService.findById(userId);
        Page<Note> notePage = noteRepo.findByNoteReceiver(userId, pageable);
        Page<NoteResponseDto> dtoPage = notePage.map(this::toDto);

        return ResponseDto.success(ResponseMessage.SUCCESS, "", dtoPage);
    }

    @Override
    public ResponseDto<Page<NoteResponseDto>> getSentNotes(Long userId, Pageable pageable) {
        userService.findById(userId);
        Page<Note> notePage = noteRepo.findByNoteWriter(userId, pageable);
        Page<NoteResponseDto> dtoPage = notePage.map(this::toDto);

        return ResponseDto.success(ResponseMessage.SUCCESS, "", dtoPage);
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
