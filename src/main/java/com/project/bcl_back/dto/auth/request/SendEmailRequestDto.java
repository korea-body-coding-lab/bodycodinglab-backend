package com.project.bcl_back.dto.auth.request;

import com.project.bcl_back.common.constants.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SendEmailRequestDto {
    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = Regex.EMAIL, message = "이메일은 abc@example.com 형식이어야 합니다.")
    private String email;
}
