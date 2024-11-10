package site.ymango.point.entity

import jakarta.persistence.*
import site.ymango.common.BaseEntity

@Entity
@Table(name = "point_wallet_history", schema = "flirting", indexes = [
    Index(name = "idx_point_wallet_history_point_wallet_id", columnList = "point_wallet_id"),
    Index(name = "idx_point_wallet_history_user_id", columnList = "user_id"),
])
class PointWalletHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_wallet_history_id")
    val pointWalletHistoryId: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "point_wallet_id")
    val pointWalletId: Long,

    @Column(name = "point_id")
    val pointId: Int,

    @Column(name = "amount")
    val amount: Int,

    @Column(name = "balance")
    val balance: Int,

    @Column(name = "summary")
    val summary: String,

) : BaseEntity()