package site.ymango.send.enums

enum class SendTemplate(
    val sendType: SendType,
    val templateId: String,
) {
    EMAIL_VERIFICATION(SendType.EMAIL, "10880"),
    PHONE_VERIFICATION(SendType.SMS, ""),
    ;
}