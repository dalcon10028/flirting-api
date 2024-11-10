package site.ymango.recommendation.v1.dto

import site.ymango.recommendation.model.RecommendProfileModel
import site.ymango.user.v1.dto.UserProfileResponse
import java.time.LocalDateTime

data class RecommendProfileResponse(
    val recommendProfileId: Long,
    var userProfileId: Long,
    var recommendedId: Long,
    var rating: Int? = null,
    val expiredAt: LocalDateTime,
    val openedAt: LocalDateTime? = null,
    val userProfile: UserProfileResponse? = null,
) {
    companion object {
        fun from(recommendProfile: RecommendProfileModel) : RecommendProfileResponse {
            return RecommendProfileResponse(
                recommendProfileId = recommendProfile.recommendProfileId,
                userProfileId = recommendProfile.userProfileId,
                recommendedId = recommendProfile.recommendedId,
                rating = recommendProfile.rating,
                expiredAt = recommendProfile.expiredAt,
                openedAt = recommendProfile.openedAt,
                userProfile = recommendProfile.userProfile?.let { UserProfileResponse.from(it) }
            )
        }
    }
}
