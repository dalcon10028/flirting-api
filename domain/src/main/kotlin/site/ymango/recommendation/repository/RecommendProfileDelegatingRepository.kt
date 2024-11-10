package site.ymango.recommendation.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.recommendation.entity.RecommendProfile

interface RecommendProfileDelegatingRepository : JpaRepository<RecommendProfile, Long> {
    fun findByUserProfileId(userProfileId: Long): List<RecommendProfile>

    fun findByUserProfileIdAndRecommendProfileId(recommendProfileId: Long, userProfileId: Long): RecommendProfile?

    fun existsByUserProfileIdAndRecommendedId(userProfileId: Long, recommendedId: Long): Boolean
}