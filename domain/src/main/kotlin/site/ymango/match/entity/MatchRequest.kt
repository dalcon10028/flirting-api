package site.ymango.match.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import site.ymango.common.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "match_request", schema = "flirting", indexes = [
    Index(name = "idx_requester_id", columnList = "requester_id"),
    Index(name = "idx_requestee_id", columnList = "requestee_id"),
])
@SQLRestriction("expired_at > now()")
@SQLDelete(sql = "UPDATE flirting.match_request SET expired_at = now() WHERE match_request_id = ?")
class MatchRequest (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_request_id")
    val matchRequestId: Long? = null,

    @Column(name = "requester_id")
    var requesterId: Long,

    @Column(name = "requestee_id")
    var requesteeId: Long,

    @Column(name = "accepted_at")
    var acceptedAt: LocalDateTime? = null,

    @Column(name = "expired_at")
    var expiredAt: LocalDateTime = LocalDateTime.now().plusDays(7),
): BaseEntity() {
    fun match(): MatchRequest {
        val matchedAt = LocalDateTime.now()
        acceptedAt = matchedAt
        expiredAt = matchedAt
        return this
    }
}