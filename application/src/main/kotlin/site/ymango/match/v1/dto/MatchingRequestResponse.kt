package site.ymango.match.v1.dto

import site.ymango.match.model.MatchRequestModel
import site.ymango.user.v1.dto.UserProfileResponse
import java.time.LocalDateTime

data class MatchingRequestResponse(
    val matchRequestId: Long,
    val requesterId: Long,
    val requesteeId: Long,
    val expiredAt: LocalDateTime,
    val userProfile: UserProfileResponse,
) {
    companion object {
        fun of(matchingRequest: MatchRequestModel) : MatchingRequestResponse {
            assert(matchingRequest.matchRequestId != null) { "matchRequestId must not be null" }
            assert(matchingRequest.userProfile != null) { "userProfile must not be null" }
            assert(matchingRequest.expiredAt != null) { "expiredAt must not be null" }

            return MatchingRequestResponse(
                matchRequestId = matchingRequest.matchRequestId!!,
                requesterId = matchingRequest.requesterId,
                requesteeId = matchingRequest.requesteeId,
                expiredAt = matchingRequest.expiredAt!!,
                userProfile = UserProfileResponse.from(matchingRequest.userProfile!!)
            )
        }
    }
}
