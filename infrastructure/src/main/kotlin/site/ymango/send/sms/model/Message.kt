package site.ymango.send.sms.model

data class Message(
    val to: String, // 수신번호, 붙임표 ( - )를 제외한 숫자만 입력 가능
    val subject: String?, // 개별 메시지 제목, LMS, MMS만 사용 가능
    val content: String, // 개별 메시지 내용 SMS: 최대 90byte, LMS: 최대 2000byte, MMS: 최대 2000byte
)
