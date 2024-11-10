package site.ymango.verification.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import site.ymango.common.BaseEntity
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.verification.enums.ReceptionType
import java.time.LocalDateTime

@Entity
@Table(name = "verification", catalog = "flirting")
@SQLRestriction("expired_at > now()")
class Verification (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    val verificationId: Long? = null,

    @Column(name = "receiver")
    val receiver: String,

    @Column(name = "device_id")
    val deviceId: String,

    @Column(name = "code")
    val code: String,

    @Column(name = "verified")
    var verified: Boolean = false,

    @Column(name = "reception_type")
    @Enumerated(EnumType.STRING)
    val receptionType: ReceptionType,

    @Column(name = "expired_at")
    var expiredAt: LocalDateTime,

) : BaseEntity() {
    fun verify(code: String): Verification {
        if (this.code == code) {
            this.verified = true
            return this
        } else {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "인증번호가 일치하지 않습니다.")
        }
    }
}