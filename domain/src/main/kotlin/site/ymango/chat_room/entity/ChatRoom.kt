package site.ymango.chat_room.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import site.ymango.common.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "chat_room", catalog = "flirting", indexes = [
    Index(name = "idx_chat_room_match_request_id", columnList = "match_request_id"),
    Index(name = "idx_chat_room_user_id", columnList = "user_id"),
])
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE chat_room SET deleted_at = CURRENT_TIMESTAMP WHERE chat_room_id = ?")
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    val chatRoomId: Long? = null,

    @Column(name = "match_request_id")
    val matchRequestId: Long,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "opponent_user_id")
    val opponentUserId: Long,

    @Column(name = "opened_at")
    var openedAt: LocalDateTime? = null,

    @Column(name = "closed_at")
    var closedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

) : BaseEntity() {
    fun open(): ChatRoom {
        this.openedAt = this.openedAt ?: LocalDateTime.now()
        return this
    }

    fun close(): ChatRoom {
        this.closedAt = this.closedAt ?: LocalDateTime.now()
        return this
    }
}