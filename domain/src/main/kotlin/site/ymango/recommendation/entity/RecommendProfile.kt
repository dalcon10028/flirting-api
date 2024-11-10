package site.ymango.recommendation.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import site.ymango.common.BaseEntity
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.recommendation.enums.RecommendType
import java.time.LocalDateTime

@Entity
@Table(name = "recommend_profile", schema = "flirting", indexes = [
    Index(name = "idx_user_profile_id", columnList = "user_profile_id"),
    Index(name = "idx_requester_id", columnList = "requester_id"),
    Index(name = "idx_reference_id", columnList = "reference_id"),
])
@SQLDelete(sql = "UPDATE recommend_profile SET expired_at = now() WHERE recommend_profile_id = ?")
@SQLRestriction("expired_at > now()")
class RecommendProfile (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_profile_id")
    val recommendProfileId: Long? = null,

    @Column(name = "user_profile_id")
    var userProfileId: Long,

    @Column(name = "recommended_id")
    var recommendedId: Long,

    @Column(name = "reference_type")
    @Enumerated(EnumType.STRING)
    var referenceType: RecommendType,

    @Column(name = "reference_id")
    var referenceId: Long? = null,

    @Column(name = "rating")
    var rating: Int? = null,

    @Column(name = "opened_at")
    var openedAt: LocalDateTime? = null,

    @Column(name = "expired_at")
    var expiredAt: LocalDateTime = LocalDateTime.now().plusDays(7),
): BaseEntity() {
    fun rate(rating: Int): RecommendProfile {
        if (rating < 1 || rating > 5) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "rating은 1~5 사이의 값이어야 합니다.")
        }
        this.rating = rating
        return this
    }

    fun expire() : RecommendProfile {
        expiredAt = LocalDateTime.now()
        return this
    }

    fun viewed() : RecommendProfile {
        openedAt = openedAt ?: LocalDateTime.now()
        return this
    }
}