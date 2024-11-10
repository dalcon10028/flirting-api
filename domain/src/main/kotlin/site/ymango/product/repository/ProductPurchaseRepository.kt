package site.ymango.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.product.entity.ProductPurchase

interface ProductPurchaseRepository : JpaRepository<ProductPurchase, Long> {
    fun findAllByUserId(uid: Long): List<ProductPurchase>
    fun findByOrderId(orderId: String): ProductPurchase?

    fun existsByUserIdAndToken(uid: Long, token: String): Boolean
}