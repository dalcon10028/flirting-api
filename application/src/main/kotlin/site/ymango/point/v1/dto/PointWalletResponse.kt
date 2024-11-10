package site.ymango.point.v1.dto

import site.ymango.point.model.PointWalletModel

data class PointWalletResponse(
    val userId: Long,
    val balance: Int,
) {
    companion object {
        fun from(model: PointWalletModel) = PointWalletResponse(
            userId = model.userId,
            balance = model.balance,
        )
    }
}
