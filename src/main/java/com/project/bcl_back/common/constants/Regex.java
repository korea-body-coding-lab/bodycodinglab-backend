package com.project.bcl_back.common.constants;

public interface Regex {
    // 사용자 ID: 영문 시작, 영문 또는 숫자 포함 5~12자
    String USER_NAME = "^[a-zA-Z][a-zA-Z0-9]{5,12}$";

    // 비밀번호: 영문, 숫자, 특수문자 각각 하나 이상 포함, 최소 8자 이상
    String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    // 사용자 이름: 한글 2~10자
    String NAME_KOREAN = "^[가-힣]{2,10}$";

    // 휴대폰 번호: 000-000(0)-0000 형식, 숫자 10~11자
    String PHONE = "^(01[0-9]{1})-([0-9]{3,4})-([0-9]{4})$";

    // 이메일: 기본 이메일 형식
    String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
}
