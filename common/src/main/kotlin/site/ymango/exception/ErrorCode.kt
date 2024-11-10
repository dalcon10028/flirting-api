package site.ymango.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    NOTNULL_PARAMETER(HttpStatus.BAD_REQUEST, "필수 입력값이 누락되었습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리소스를 찾을 수 없습니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "해당 리소스가 이미 존재합니다."),
    DUPLICATE_PHONE(HttpStatus.CONFLICT, "이미 가입된 전화번호입니다."),
    DUPLICATE_DEVICE(HttpStatus.CONFLICT, "이미 가입된 기기입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    WITHDRAWAL_USER(HttpStatus.FORBIDDEN, "탈퇴한 회원입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    FCM_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FCM 전송에 실패하였습니다."),
    INVALID_USER_PROFILE(HttpStatus.BAD_REQUEST, "조회할 수 없는 사용자 프로필입니다."),
    RECOMMEND_INACTIVE_USER_PROFILE(HttpStatus.BAD_REQUEST, "추천 비활성화된 프로필입니다."),
    NO_RECOMMENDABLE_PROFILE(HttpStatus.BAD_REQUEST, "추천할 수 있는 프로필이 더이상 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),
    FAIL_TO_GET_PURCHASE(HttpStatus.INTERNAL_SERVER_ERROR, "결제 정보를 가져오는데 실패했습니다."),

    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생하였습니다."),
}