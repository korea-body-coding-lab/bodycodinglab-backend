package com.project.bcl_back.common.constants;

public interface ApiMappingPattern {
    String AUTH_API = "/api/v1/auth";
    String ADMIN_API = "/api/v1/admin";
    String USER_API = "/api/v1/users";
    String TRAINER_API = "/api/v1/trainers";
    String MEMBER_API = "/api/v1/members";

    String COUPON_API = "/api/v1/coupons";
    String MEMBER_COUPON_API = "/api/v1/members/coupons";
    String TRAINER_COUPON_API = "/api/v1/trainer/coupons";


    String BOARD_API = "/api/v1/personal-community-boards" + "/{matchId}";
    String BOARD_COMMENT_API = BOARD_API +"/{categoryId}"+ "/{boardId}" + "/comments";
    String NOTE_API = "/api/v1/notes";

    String FILE_API = "/api/v1/files";

    String MEMBER_FORM_API = "api/v1/members/forms";

    String MEMBER_MATCH_WAITING_LIST_API = "api/v1/members/match-waiting-lists";
    String TRAINER_MATCH_WAITING_LIST_API = "api/v1/trainer/match-waiting-lists";

    String MEMER_MATCH_API = "api/v1/members/match-success-lists";
    String TRAINER_MATCH_API = "api/v1/trainer/match-success-lists";

    String SUBSCRIPTION_API = "api/v1/members/subscriptions";
}
