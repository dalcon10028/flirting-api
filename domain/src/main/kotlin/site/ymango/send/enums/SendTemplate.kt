package site.ymango.send.enums

enum class SendTemplate(
    val sendType: SendType,
) {
    EMAIL_VERIFICATION(SendType.EMAIL),
    PHONE_VERIFICATION(SendType.SMS),
    EMAIL_TEST(SendType.EMAIL),
    SMS_TEST(SendType.SMS),
    PUSH_TEST(SendType.PUSH),
    TODAYS_RECOMMENDATION(SendType.PUSH), // 오늘의 추천
    FLIRTING_RECEIVED(SendType.PUSH), // 플러팅 받음
    MATCH_COMPLETE(SendType.PUSH), // 매칭 성공
    CHAT_MESSAGE(SendType.PUSH), // 채팅 메시지
    ;
}