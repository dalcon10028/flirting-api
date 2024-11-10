package site.ymango.point.v1.dto

import site.ymango.point.model.PointWalletHistoryModel
import java.time.LocalDateTime

data class PointHistoryResponse(
    val pointWalletHistoryId: Long,
    val pointWalletId: Long,
    val pointId: Int,
    val amount: Int,
    val balance: Int,
    val summary: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(pointWalletHistory: PointWalletHistoryModel): PointHistoryResponse =
            PointHistoryResponse(
                pointWalletHistoryId = pointWalletHistory.pointWalletHistoryId,
                pointWalletId = pointWalletHistory.pointWalletId,
                pointId = pointWalletHistory.pointId,
                amount = pointWalletHistory.amount,
                balance = pointWalletHistory.balance,
                summary = pointWalletHistory.summary,
                createdAt = pointWalletHistory.createdAt,
            )
    }
}
