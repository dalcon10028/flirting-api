package site.ymango.point.model

import site.ymango.point.entity.PointWallet
import java.time.LocalDateTime

data class PointWalletModel(
    val pointWalletId: Long,
    val userId: Long,
    val balance: Int,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(pointWallet: PointWallet): PointWalletModel {
            assert(pointWallet.pointWalletId != null)
            assert(pointWallet.updatedAt != null)

            return PointWalletModel(
                pointWalletId = pointWallet.pointWalletId!!,
                userId = pointWallet.userId,
                balance = pointWallet.balance,
                updatedAt = pointWallet.updatedAt!!,
            )
        }
    }
}
