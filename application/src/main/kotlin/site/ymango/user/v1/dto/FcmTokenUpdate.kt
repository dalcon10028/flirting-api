package site.ymango.user.v1.dto

import jakarta.validation.constraints.NotBlank

data class FcmTokenUpdate(
    @field:NotBlank
    val fcmToken: String
)
