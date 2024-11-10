package site.ymango.verification.v1.dto

import site.ymango.verification.model.VerificationModel
import java.time.LocalDateTime

data class VerificationResponse(
    val receptionType: String,
    val receiver: String,
    val verified: Boolean,
    val code: String,
    val deviceId: String
) {
    companion object {
        fun of(verification: VerificationModel) = VerificationResponse(
            receptionType = verification.receptionType.name,
            receiver = verification.receiver,
            verified = verification.verified,
            code = verification.code,
            deviceId = verification.deviceId,
        )
    }
}
