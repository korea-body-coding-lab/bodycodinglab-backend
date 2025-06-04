package com.project.bcl_back.dto.note.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class NoteResponseDto {
    private String noteText;
    private Long noteWriter;
    private Long noteReceiver;
    private LocalDateTime noteCreateTime;
}
