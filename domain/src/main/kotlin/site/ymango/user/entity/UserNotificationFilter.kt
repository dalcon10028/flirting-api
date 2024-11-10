package site.ymango.user.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import site.ymango.common.BaseEntity
import site.ymango.user.enums.PushNotification

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "user_notification_filter", catalog = "flirting", indexes = [
    Index(name = "idx_user_notification_user_id", columnList = "user_id")
], uniqueConstraints = [
    UniqueConstraint(columnNames = ["user_id", "send_template"], name = "uk_user_notification_user_id_send_template")
])
class UserNotificationFilter(
    @Id
    @Column(name = "user_notification_filter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userNotificationFilterId: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "send_template")
    var sendTemplate: PushNotification,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    var user: User? = null,
) : BaseEntity()