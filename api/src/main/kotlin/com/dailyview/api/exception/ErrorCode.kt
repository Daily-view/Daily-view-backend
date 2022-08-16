package com.dailyview.api.exception

enum class ErrorCode {
    EMAIL_IS_DUPLICATED,
    NICKNAME_IS_DUPLICATED,
    MEMBER_DOES_NOT_EXISTS,
    CHECK_YOUR_ACCOUNT,
    INVALID_JWT_TOKEN,
    REFRESH_TOKEN_EXPIRED
}
