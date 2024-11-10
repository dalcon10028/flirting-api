package site.ymango.point.model

import site.ymango.point.entity.PointWalletHistory
import java.time.LocalDateTime

data class PointWalletHistoryModel(
    val pointWalletHistoryId: Long,
    val userId: Long,
    val pointWalletId: Long,
    val pointId: Int,
    val amount: Int,
    val balance: Int,
    val summary: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(pointWalletHistory: PointWalletHistory): PointWalletHistoryModel {
            assert(pointWalletHistory.pointWalletHistoryId != null)
            assert(pointWalletHistory.createdAt != null)

            return PointWalletHistoryModel(
                pointWalletHistoryId = pointWalletHistory.pointWalletHistoryId!!,
                userId = pointWalletHistory.userId,
                pointWalletId = pointWalletHistory.pointWalletId,
                pointId = pointWalletHistory.pointId,
                amount = pointWalletHistory.amount,
                balance = pointWalletHistory.balance,
                summary = pointWalletHistory.summary,
                createdAt = pointWalletHistory.createdAt!!,
            )
        }
    }
}
