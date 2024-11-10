package site.ymango.user.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import site.ymango.common.BaseEntity
import site.ymango.user.enums.*
import java.time.LocalDateTime

@Entity
@Table(name = "user", catalog = "flirting",
    indexes = [Index(name = "user_ix_email", columnList = "email")],
    uniqueConstraints = [UniqueConstraint(name = "user_uk_phone_number_device_id", columnNames = ["phone_number", "device_id"])]
)
class User(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,

    @Column(name = "phone_number", nullable = false, unique = true)
    var phoneNumber: String,

    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "device_id", nullable = false, unique = true)
    var deviceId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: UserStatus = UserStatus.ACTIVE,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole,

    @CreatedDate
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    var userProfile: UserProfile? = null,

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    var userAdditionalInformation: UserAdditionalInformation? = null,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    var userNotificationFilters: List<UserNotificationFilter>,
): BaseEntity()