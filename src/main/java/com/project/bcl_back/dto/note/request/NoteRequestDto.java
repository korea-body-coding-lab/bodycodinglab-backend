package com.project.bcl_back.dto.note.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoteRequestDto {
    @NotBlank(message = "텍스트는 필수 입력 값입니다.")
    private String noteText;
    @NotNull
    private Long noteWriter;
    @NotNull(message = "받는 사람은 필수 입력 값입니다.")
    private Long noteReceiver;
}
