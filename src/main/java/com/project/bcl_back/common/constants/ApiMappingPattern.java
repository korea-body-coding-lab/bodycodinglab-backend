package com.project.bcl_back.common.constants;

public interface ApiMappingPattern {
    public static final String AUTH_API = "/api/v1/auth";
    public static final String USER_API = "/api/v1/users";
    public static final String TRAINER_API = "/api/v1/trainers";


    public static final String MEMBER_COUPON_API = "api/v1/members/me/coupons";
    public static final String TRAINER_COUPON_API = "api/v1/trainers/me/coupons";

    public static final String BOARD_API = "/api/v1/personal-community-boards";
    public static final String BOARD_COMMENT_API = BOARD_API + "/comments";
    public static final String NOTE_API = "/api/v1/notes";
}
