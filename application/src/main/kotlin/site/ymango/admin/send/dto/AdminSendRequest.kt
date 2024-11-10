package site.ymango.admin.send.dto

import jakarta.validation.constraints.NotNull
import site.ymango.send.enums.SendTemplate

data class AdminSendRequest(
    val userId: Long?,

    val receiver: String?,

    val parameters: Map<String, Any>,

    val sendTemplate: SendTemplate,
)
