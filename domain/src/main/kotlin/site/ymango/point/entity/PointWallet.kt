package site.ymango.point.entity

import site.ymango.common.BaseEntity
import java.time.LocalDateTime

import jakarta.persistence.*
import jakarta.persistence.Id
import org.springframework.data.annotation.*
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode

@Entity
@Table(name = "point_wallet", schema = "flirting", indexes = [
    Index(name = "idx_point_wallet_user_id", columnList = "user_id")
], uniqueConstraints = [
    UniqueConstraint(name = "uk_point_wallet_user_id", columnNames = ["user_id"])
])
class PointWallet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_wallet_id")
    val pointWalletId: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "balance")
    var balance: Int = 0,

    @CreatedDate
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,
) : BaseEntity() {

    /**
     * 포인트 사용
     */
    fun applyPoint(amount: Int): Int {
        balance += amount
        if (balance < 0) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "포인트 잔액이 부족합니다.")
        }
        return balance
    }
}