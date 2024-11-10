package site.ymango.recommendation.model

import site.ymango.recommendation.entity.RecommendProfile
import site.ymango.recommendation.enums.RecommendType
import site.ymango.user.model.UserProfileModel
import java.time.LocalDateTime

data class RecommendProfileModel(
    val recommendProfileId: Long,
    val userProfileId: Long,
    val recommendedId: Long,
    val rating: Int? = null,
    val referenceType: RecommendType,
    val referenceId: Long?,
    val expiredAt: LocalDateTime,
    val openedAt: LocalDateTime? = null,
    var userProfile: UserProfileModel? = null,
    val createdAt: LocalDateTime? = null,
) {
    companion object {
        fun from(recommendProfile: RecommendProfile) : RecommendProfileModel {
            assert(recommendProfile.recommendProfileId != null) { "recommendProfileId must not be null" }

            return RecommendProfileModel(
                recommendProfileId = recommendProfile.recommendProfileId!!,
                userProfileId = recommendProfile.userProfileId,
                recommendedId = recommendProfile.recommendedId,
                rating = recommendProfile.rating,
                referenceType = recommendProfile.referenceType,
                referenceId = recommendProfile.referenceId,
                expiredAt = recommendProfile.expiredAt,
                openedAt = recommendProfile.openedAt,
                createdAt = recommendProfile.createdAt,
            )
        }

        fun ofNullable(recommendProfile: RecommendProfile, userProfile: UserProfileModel?) : RecommendProfileModel? {
            assert(recommendProfile.recommendProfileId != null) { "recommendProfileId must not be null" }

            return userProfile?.let {
                RecommendProfileModel(
                    recommendProfileId = recommendProfile.recommendProfileId!!,
                    userProfileId = recommendProfile.userProfileId,
                    recommendedId = recommendProfile.recommendedId,
                    rating = recommendProfile.rating,
                    referenceType = recommendProfile.referenceType,
                    referenceId = recommendProfile.referenceId,
                    expiredAt = recommendProfile.expiredAt,
                    openedAt = recommendProfile.openedAt,
                    userProfile = userProfile,
                )
            }
        }

        fun of(recommendProfile: RecommendProfile, userProfile: UserProfileModel) : RecommendProfileModel {
            assert(recommendProfile.recommendProfileId != null) { "recommendProfileId must not be null" }

            return RecommendProfileModel(
                recommendProfileId = recommendProfile.recommendProfileId!!,
                userProfileId = recommendProfile.userProfileId,
                recommendedId = recommendProfile.recommendedId,
                rating = recommendProfile.rating,
                referenceType = recommendProfile.referenceType,
                referenceId = recommendProfile.referenceId,
                expiredAt = recommendProfile.expiredAt,
                openedAt = recommendProfile.openedAt,
                userProfile = userProfile,
            )
        }
    }
}
