package site.ymango.send.email.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SendEmailResponse(
    @JsonProperty("requestId")
    val requestId: String?,

    @JsonProperty("count")
    val count: Int?,
)
