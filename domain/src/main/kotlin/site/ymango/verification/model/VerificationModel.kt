package site.ymango.verification.model

import site.ymango.verification.entity.Verification
import site.ymango.verification.enums.ReceptionType
import java.time.LocalDateTime

data class VerificationModel (
    val receptionType: ReceptionType,
    val receiver: String,
    val verified: Boolean,
    val code: String,
    val deviceId: String,
) {
    companion object {
        fun from(verification: Verification) = VerificationModel(
            receptionType = verification.receptionType,
            receiver = verification.receiver,
            verified = verification.verified,
            code = verification.code,
            deviceId = verification.deviceId,
        )
    }

}
