package site.ymango.admin.product.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class RefundRequest(
    @field:NotNull
    val userId: Long,

    @field:NotBlank
    val orderId: String,

    @field:NotBlank
    val productId: String,

    @field:NotBlank
    val token: String,
)
