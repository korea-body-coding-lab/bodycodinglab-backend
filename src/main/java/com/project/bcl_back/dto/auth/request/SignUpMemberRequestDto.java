package com.project.bcl_back.dto.auth.request;

import com.project.bcl_back.common.constants.Regex;
import com.project.bcl_back.common.enums.user.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpMemberRequestDto {
    @NotBlank(message = "아이디는 필수 항목입니다.")
    @Pattern(regexp = Regex.USER_NAME, message = "아이디는 5~12자의 영문/숫자만 사용 가능하며, 영문으로 시작하여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = Regex.PASSWORD, message = "비밀번호는 영문/숫자/특수문자를 포함한 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Pattern(regexp = Regex.NAME_KOREAN, message = "이름은 2~10자의 한글만 사용 가능합니다.")
    private String name;

    @NotBlank(message = "생년월일은 필수 항목입니다.")
    @Pattern(regexp = Regex.BIRTHDATE, message = "생년월일은 YYYY-MM-DD 형식이여야 합니다.")
    private String birthdate;

    @NotBlank(message = "성별은 필수 항목입니다.")
    private Gender gender;

    @NotBlank(message = "휴대폰 번호는 필수 항목입니다.")
    @Pattern(regexp = Regex.PHONE, message = "휴대폰 번호는 000-0000-0000 형식이어야 합니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = Regex.EMAIL, message = "이메일은 abc@example.com 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;

    private Long profileImageId;
}
