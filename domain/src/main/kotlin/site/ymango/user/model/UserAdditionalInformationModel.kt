package site.ymango.user.model

import site.ymango.user.entity.UserAdditionalInformation
import java.time.LocalDateTime

data class UserAdditionalInformationModel(
    val userAdditionalInformationId: Long? = null,
    val userId: Long? = null,
    val extraEmail: String? = null,
    val fcmToken: String,
    val fcmTokenUpdatedAt: LocalDateTime? = null,
    val phoneBlockUsedAt: LocalDateTime? = null,
    val marketingAgreedAt: LocalDateTime? = null,
    val lastAccessedAt: LocalDateTime? = null,
) {
    companion object {
        fun from(userAdditionalInformation: UserAdditionalInformation) = UserAdditionalInformationModel(
            userAdditionalInformationId = userAdditionalInformation.userAdditionalInformationId,
            userId = userAdditionalInformation.userId,
            extraEmail = userAdditionalInformation.extraEmail,
            fcmToken = userAdditionalInformation.fcmToken,
            fcmTokenUpdatedAt = userAdditionalInformation.fcmTokenUpdatedAt,
            phoneBlockUsedAt = userAdditionalInformation.phoneBlockUsedAt,
            marketingAgreedAt = userAdditionalInformation.marketingAgreedAt,
            lastAccessedAt = userAdditionalInformation.lastAccessedAt,
        )
    }
}
