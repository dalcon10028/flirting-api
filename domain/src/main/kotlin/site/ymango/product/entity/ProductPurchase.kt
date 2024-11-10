package site.ymango.product.entity

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import site.ymango.common.BaseEntity
import site.ymango.purchase.enums.PurchaseState
import java.time.LocalDateTime

@Entity
@Table(name = "product_purchase", schema = "flirting",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "order_id"])],
    indexes = [Index(columnList = "user_id")]
)
class ProductPurchase (
    @Id
    @Column(name = "product_purchase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productPurchaseId: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "product_id")
    val productId: String,

    @Column(name = "point_id")
    val pointId: Int,

    @Column(name = "point_amount")
    val pointAmount: Int,

    @Column(name = "token")
    val token: String,

    @Column(name = "order_id", unique = true)
    val orderId: String,

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    var state: PurchaseState,

    @Column(name = "purchased_at")
    val purchasedAt: LocalDateTime,

    @Type(JsonType::class)
    @Column(name = "response", columnDefinition = "json")
    var response: Any,

    @CreatedDate
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

) : BaseEntity() {

    fun refund(response: Any) : ProductPurchase {
        state = PurchaseState.CANCELLED
        this.response = response
        return this
    }
}