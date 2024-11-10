package site.ymango.match.model

import site.ymango.match.entity.MatchRequest
import site.ymango.user.model.UserProfileModel
import java.time.LocalDateTime

data class MatchRequestModel(
    val matchRequestId: Long? = null,
    val requesterId: Long,
    val requesteeId: Long,
    val acceptedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
    val expiredAt: LocalDateTime? = null,
    val userProfile: UserProfileModel? = null,
) {
    companion object {
        fun of(matchRequest: MatchRequest, userProfile: UserProfileModel) : MatchRequestModel {
            assert(matchRequest.matchRequestId != null) { "matchRequestId must not be null" }

            return MatchRequestModel(
                matchRequestId = matchRequest.matchRequestId,
                requesterId = matchRequest.requesterId,
                requesteeId = matchRequest.requesteeId,
                acceptedAt = matchRequest.acceptedAt,
                expiredAt = matchRequest.expiredAt,
                userProfile = userProfile,
            )
        }

        fun ofNullable(matchRequest: MatchRequest, userProfile: UserProfileModel?) : MatchRequestModel? {
            return userProfile?.let {
                return MatchRequestModel(
                    matchRequestId = matchRequest.matchRequestId,
                    requesterId = matchRequest.requesterId,
                    requesteeId = matchRequest.requesteeId,
                    acceptedAt = matchRequest.acceptedAt,
                    expiredAt = matchRequest.expiredAt,
                    userProfile = userProfile,
                )
            }
        }
    }
}
