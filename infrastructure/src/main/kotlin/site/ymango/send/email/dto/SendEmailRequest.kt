package site.ymango.send.email.dto

import com.fasterxml.jackson.annotation.JsonProperty
import site.ymango.send.email.model.Recipient

data class SendEmailRequest(
    @JsonProperty("senderName")
    val senderName: String,

    @JsonProperty("templateSid")
    val templateSid: String,

    @JsonProperty("recipients")
    val recipients: List<Recipient> = listOf(),
)
