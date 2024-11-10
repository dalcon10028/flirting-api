package site.ymango.send.sms.dto

data class SendSmsResponse(
    val requestId: String, // 요청 아이디
    val requestTime: String, // 요청 시간 (yyyy-MM-dd'T'HH:mm:ss.SSS)
    val statusCode: String, // 요청 결과 코드 202: 성공, 그 외: 실패, HTTP Status 규격을 따름
    val statusName: String, // 요청 결과 코드명 success: 성공, fail: 실패
)
