package site.ymango.product.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import site.ymango.common.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "product", schema = "flirting")
@SQLDelete(sql = "UPDATE product SET deleted_at = now() WHERE product_id = ?")
class Product (
    @Id
    @Column(name = "product_id")
    val productId: String,

    @Column(name = "point_id")
    var pointId: Int,

    @Column(name = "point_amount")
    var pointAmount: Int,

    @Column(name = "image_url")
    var imageUrl: String,

    @Column(name = "original_price")
    var originalPrice: Int,

    @Column(name = "sale_price")
    var salePrice: Int,

    @Column(name = "label_color")
    var labelColor: String?,

    @Column(name = "label_text")
    var labelText: String?,

    @Column(name = "sort")
    var sort: Int,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,
) : BaseEntity()