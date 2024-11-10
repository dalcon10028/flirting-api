package site.ymango.user.enums

import site.ymango.send.enums.SendTemplate

enum class PushNotification(
    val sendTemplate: SendTemplate,
) {
    TODAYS_RECOMMENDATION(SendTemplate.TODAYS_RECOMMENDATION),
    FLIRTING_RECEIVED(SendTemplate.FLIRTING_RECEIVED),
    MATCH_COMPLETE(SendTemplate.MATCH_COMPLETE),
    CHAT_MESSAGE(SendTemplate.CHAT_MESSAGE)
    ;
}