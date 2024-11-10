package site.ymango.purchase.model

import site.ymango.purchase.enums.PurchaseState
import java.time.LocalDateTime

data class Receipt(
    val productId: String,
    val orderId: String,
    val purchaseToken: String,
    val purchaseState: PurchaseState,
    val purchaseTime: LocalDateTime,
    val response: Any
)
