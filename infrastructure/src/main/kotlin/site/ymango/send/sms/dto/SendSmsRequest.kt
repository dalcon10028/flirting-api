package site.ymango.send.sms.dto

import com.fasterxml.jackson.annotation.JsonProperty
import site.ymango.send.sms.enums.ContentType
import site.ymango.send.sms.enums.SmsType
import site.ymango.send.sms.model.File
import site.ymango.send.sms.model.Message

data class SendSmsRequest(
    @JsonProperty("type")
    val type: SmsType, // SMS, LMS, MMS (소문자 가능)

    @JsonProperty("contentType")
    val contentType: ContentType?, // default: COMM

    @JsonProperty("countryCode")
    val countryCode: String?, // default: 82

    @JsonProperty("from")
    val from: String = "01012345678", // 사전 등록된 발신번호만 사용 가능

    @JsonProperty("subject")
    val subject: String?, // LMS, MMS만 사용 가능

    @JsonProperty("content")
    val content: String, // 90byte 이상일 경우 LMS, MMS로 발송

    @JsonProperty("messages")
    val messages: List<Message>, // 메시지 정보, 최대 100건

    @JsonProperty("files")
    val files: List<File>? = null, // MMS에서만 사용 가능

    @JsonProperty("reserveTime")
    val reserveTime: String? = null, // 메시지 발송 예약 일시 (yyyy-MM-dd HH:mm)

    @JsonProperty("reserveTimeZone")
    val reserveTimeZone: String? = null, // 예약 일시 타임존 (기본: Asia/Seoul)
)
