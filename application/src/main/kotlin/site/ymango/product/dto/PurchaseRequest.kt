package site.ymango.product.dto

data class PurchaseRequest(
    val productId: String,
    val token: String,
)
