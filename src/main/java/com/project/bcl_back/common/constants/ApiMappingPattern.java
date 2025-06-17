package com.project.bcl_back.common.constants;

public interface ApiMappingPattern {
    String AUTH_API = "/api/v1/auth";
    String ADMIN_API = "/api/v1/admin";
    String USER_API = "/api/v1/users";
    String TRAINER_API = "/api/v1/trainers";

    String MEMBER_COUPON_API = "/api/v1/members/me/coupons";
    String TRAINER_COUPON_API = "/api/v1/trainers/me/coupons";

    String BOARD_API = "/api/v1/personal-community-boards";
    String BOARD_COMMENT_API = BOARD_API + "/{boardId}" + "/comments";
    String NOTE_API = "/api/v1/notes";
}
