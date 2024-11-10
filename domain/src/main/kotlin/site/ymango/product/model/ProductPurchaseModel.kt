package site.ymango.product.model

import site.ymango.product.entity.ProductPurchase
import site.ymango.purchase.enums.PurchaseState
import java.time.LocalDateTime

data class ProductPurchaseModel(
    val userId: Long,
    val productId: String,
    val pointAmount: Int,
    val token: String,
    val orderId: String,
    val state: PurchaseState,
    val purchasedAt: LocalDateTime,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(productPurchase: ProductPurchase): ProductPurchaseModel {
            assert(productPurchase.createdAt != null)

            return ProductPurchaseModel(
                userId = productPurchase.userId,
                productId = productPurchase.productId,
                pointAmount = productPurchase.pointAmount,
                token = productPurchase.token,
                orderId = productPurchase.orderId,
                state = productPurchase.state,
                purchasedAt = productPurchase.purchasedAt,
                createdAt = productPurchase.createdAt!!
            )
        }
    }
}
