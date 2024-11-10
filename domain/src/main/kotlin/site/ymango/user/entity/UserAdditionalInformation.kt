package site.ymango.user.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import site.ymango.common.BaseEntity
import java.time.LocalDateTime

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "user_additional_information", catalog = "flirting", indexes = [
    Index(name = "idx_user_additional_information_user_id", columnList = "user_id")
])
class UserAdditionalInformation(
    @Id
    @Column(name = "user_additional_information_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userAdditionalInformationId: Long? = null,

    @Column(name = "user_id", nullable = false, unique = true)
    var userId: Long?,

    @Column(name = "extra_email")
    var extraEmail: String? = null,

    @Column(name = "fcm_token")
    var fcmToken: String,

    @Column(name = "phone_block_used_at")
    var phoneBlockUsedAt: LocalDateTime? = null,

    @CreatedDate
    @Column(name = "fcm_token_updated_at")
    var fcmTokenUpdatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "marketing_agreed_at")
    var marketingAgreedAt: LocalDateTime? = null,

    @CreatedDate
    @Column(name = "last_accessed_at")
    var lastAccessedAt: LocalDateTime = LocalDateTime.now(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    var user: User? = null,
) : BaseEntity()