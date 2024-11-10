package site.ymango.send.email.model

import com.fasterxml.jackson.annotation.JsonProperty
import site.ymango.send.email.enums.*

data class Recipient(
    @JsonProperty("address")
    val address: String,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("type")
    val type: RecipientType = RecipientType.R,

    @JsonProperty("parameters")
    val parameters: Map<String, String> = mapOf()
)
