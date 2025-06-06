package com.project.bcl_back.dto.auth.request;

import com.project.bcl_back.common.constants.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public class SendMailRequestDto {
    @NotBlank(message = "아이디는 필수 항목입니다.")
    private String username;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Pattern(regexp = Regex.NAME_KOREAN, message = "이름은 2~10자의 한글만 사용 가능합니다.")
    private String name;

    @NotBlank(message = "생년월일은 필수 항목입니다.")
    @Pattern(regexp = Regex.BIRTHDATE, message = "생년월일은 YYYY-MM-DD 형식이여야 합니다.")
    private Date birthdate;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = Regex.EMAIL, message = "이메일은 abc@example.com 형식이어야 합니다.")
    private String email;

}
