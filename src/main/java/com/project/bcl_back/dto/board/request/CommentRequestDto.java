package com.project.bcl_back.dto.board.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class CommentRequestDto {
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String commentContent;

    private Long commenterId;
}
