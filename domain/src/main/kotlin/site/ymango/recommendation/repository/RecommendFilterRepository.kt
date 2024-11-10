package site.ymango.recommendation.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.recommendation.entity.RecommendFilter

interface RecommendFilterRepository : JpaRepository<RecommendFilter, Long> {
    fun deleteByUserProfileId(userProfileId: Long)
    fun existsByUserProfileId(userProfileId: Long): Boolean
}