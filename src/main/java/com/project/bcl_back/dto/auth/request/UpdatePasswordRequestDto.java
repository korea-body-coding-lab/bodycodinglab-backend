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
public class UpdatePasswordRequestDto {
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = Regex.PASSWORD, message = "비밀번호는 영문/숫자/특수문자를 포함한 최소 8자 이상이어야 합니다.")
    private String newPassword;

    @NotBlank(message = "비밀번호 확인은 필수 항목 입니다.")
    private String confirmPassword;
}
