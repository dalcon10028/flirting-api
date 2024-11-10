package site.ymango.match.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import site.ymango.match.entity.MatchRequest

@Repository
interface MatchRequestRepository : JpaRepository<MatchRequest, Long> {
    fun findByRequesterId(userProfileId: Long): List<MatchRequest>

    fun findByRequesteeId(userProfileId: Long): List<MatchRequest>

    @Query("SELECT m FROM MatchRequest m WHERE m.matchRequestId = :matchRequestId AND (m.requesterId = :userProfileId OR m.requesteeId = :userProfileId)")
    fun findByMatchRequestIdAndUserProfileId(matchRequestId: Long, userProfileId: Long): MatchRequest?
    fun findByRequesterIdAndRequesteeId(requesterId: Long, requesteeId: Long): MatchRequest?

    fun existsByRequesterIdAndRequesteeId(requesterId: Long, requesteeId: Long): Boolean
    fun findByMatchRequestIdAndRequesterId(matchRequestId: Long, requesterId: Long): MatchRequest?
}