package com.project.bcl_back.dto.user.request;

import com.project.bcl_back.common.constants.Regex;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberUpdateRequestDto {
    @Pattern(regexp = Regex.NAME_KOREAN, message = "이름은 2~10자의 한글만 사용 가능합니다.")
    private String name;
    private String memberAddress;
    private Long profileImageId;
}
