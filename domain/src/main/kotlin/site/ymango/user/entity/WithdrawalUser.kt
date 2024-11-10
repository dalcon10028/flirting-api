package site.ymango.user.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import site.ymango.common.BaseEntity

@Entity
@Table(name = "withdrawal_user", catalog = "flirting")
@SQLRestriction("DATEDIFF(NOW(), created_at) <= 14")
class WithdrawalUser(
    @Id
    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "phone_number")
    val phoneNumber: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "device_id")
    val deviceId: String,

) : BaseEntity()
