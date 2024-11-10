package site.ymango.user.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import site.ymango.user.entity.UserProfile
import site.ymango.user.enums.Gender
import site.ymango.user.enums.UserProfileStatus

@Repository
interface UserProfileRepository : JpaRepository<UserProfile, Long> {
    fun findByUserId(userId: Long): UserProfile?

    fun findByUserProfileIdAndStatusIn(userProfileId: Long, status: Collection<UserProfileStatus>): UserProfile?

    fun findByUserProfileIdAndStatus(userProfileId: Long, status: UserProfileStatus = UserProfileStatus.ACTIVE): UserProfile?

    @EntityGraph(attributePaths = ["user"])
    fun findByUserProfileId(userProfileId: Long): UserProfile?

    fun existsByUserProfileIdAndStatus(profileId: Long, status: UserProfileStatus = UserProfileStatus.ACTIVE): Boolean

//    @Query("""
//        select *
//        from flirting.user_profile
//        where gender = :targetGender
//            and status = 'ACTIVE'
//            and preferred_mbti in (:preferedMbtis)
//            and user_profile_id not in (
//                select requestee_id
//                from flirting.match_request
//                where (expired_at > now() and requester_id = :userProfileId) or (accepted_at is not null and requester_id = :userProfileId)
//                )
//            and user_profile_id not in (
//               select distinct recommended_id
//               from flirting.recommend_profile
//               where user_profile_id = :userProfileId and expired_at > now() - interval 7 day
//           )
//           and st_distance(
//             location,
//             point(:#{#location.longitude}, :#{#location.latitude})
//           ) < 10000000 -- 10,000,000 미터(또는 10,000 km)
//         order by rand()
//         limit 1
//
//    """, nativeQuery = true)
//    fun generateRecommendProfile(userProfileId: Long, targetGender: Gender, preferedMbtis: List<MBTI>, location: Location): UserProfile?


    /**
     * -- AND ST_Distance(
     *             --    up.location,
     *             --    POINT(:#{#location.longitude}, :#{#location.latitude})
     *             -- ) < 10000000
     */
    @Query("""
        SELECT up.*
        FROM flirting.user_profile up
        LEFT JOIN (
            SELECT mr.requestee_id
            FROM flirting.match_request mr
            WHERE (mr.expired_at > NOW() AND mr.requester_id = :userProfileId) 
            OR (mr.accepted_at IS NOT NULL AND mr.requester_id = :userProfileId)
        ) AS excluded_match_requests ON up.user_profile_id = excluded_match_requests.requestee_id
        LEFT JOIN (
            SELECT DISTINCT rp.recommended_id
            FROM flirting.recommend_profile rp
            WHERE rp.user_profile_id = :userProfileId AND rp.expired_at > NOW() - INTERVAL 7 DAY
        ) AS excluded_recommend_profiles ON up.user_profile_id = excluded_recommend_profiles.recommended_id
        WHERE up.gender = :#{#targetGender.name}
            AND up.status = 'ACTIVE'
            AND up.preferred_mbti IN (:preferredMbtis)
            AND excluded_match_requests.requestee_id IS NULL
            AND excluded_recommend_profiles.recommended_id IS NULL
        ORDER BY RAND()
        LIMIT 1 
    """, nativeQuery = true)
    fun generateRecommendProfile(
        @Param("userProfileId") userProfileId: Long,
        @Param("targetGender") targetGender: Gender,
        @Param("preferredMbtis") preferredMbtis: List<String>
    ): UserProfile?
}