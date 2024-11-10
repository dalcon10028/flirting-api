package site.ymango.send.entity

import io.hypersistence.utils.hibernate.type.json.JsonType
import org.hibernate.annotations.Type
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import site.ymango.common.BaseEntity
import java.time.LocalDateTime
import jakarta.persistence.*
import site.ymango.send.enums.*

@Entity
@Table(name = "send_history", schema = "flirting", indexes = [
    Index(name = "idx_send_history_user_id", columnList = "user_id"),
    Index(name = "idx_send_history_receiver", columnList = "receiver"),
    Index(name = "idx_send_history_message_id", columnList = "message_id")
])
 class SendHistory (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "send_history_id")
    val sendHistoryId: Long? = null,

    @Column(name = "send_type")
    @Enumerated(EnumType.STRING)
    val sendType: SendType,

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    val category: SendCategory,

    @Column(name = "receiver")
    val receiver: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: SendStatus = SendStatus.PROCESSING,

    @Column(name = "user_id")
    val userId: Long? = null,

    @Column(name = "template_code")
    val templateCode: String,

    @Column(name = "message_id")
    val messageId: String? = null,

    @Type(JsonType::class)
    @Column(name = "parameters", columnDefinition = "json")
    val parameters: Map<String, Any> = emptyMap(),

    @Column(name = "title")
    val title: String? = null,

    @Column(name = "content")
    var content: String? = null,

    @Type(JsonType::class)
    @Column(name = "response", columnDefinition = "json")
    var response: Map<String, Any> = emptyMap(),

    @Column(name = "fail_reason")
    var failReason: String? = null,

    @CreatedDate
    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity() {

    fun delivered(response: Map<String, Any>) : SendHistory {
        this.status = SendStatus.DELIVERED
        this.response = response
        return this
    }

    fun rendered(content: String) : SendHistory {
        this.status = SendStatus.TEMPLATE_RENDERED
        this.content = content
        return this
    }

    fun failed(message: String?): SendHistory {
        this.status = SendStatus.FAILED
        this.failReason = message
        return this
    }
}