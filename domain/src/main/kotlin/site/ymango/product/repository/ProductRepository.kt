package site.ymango.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.product.entity.Product

interface ProductRepository : JpaRepository<Product, String> {
    fun findAllByDeletedAtIsNull(): List<Product>

    fun findByProductIdAndDeletedAtIsNotNull(productId: String): Product?

    fun existsByProductId(productId: String): Boolean
}