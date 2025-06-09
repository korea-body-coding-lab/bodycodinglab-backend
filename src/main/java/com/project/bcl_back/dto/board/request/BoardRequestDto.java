package com.project.bcl_back.dto.board.request;


import com.project.bcl_back.dto.board.CategoryDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Valid
public class BoardRequestDto {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private CategoryDto category;


    private Long matchId;

    private Long writerId;

    private Long viewCount;

}
