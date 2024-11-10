package site.ymango.product.model

import site.ymango.product.entity.Product

data class ProductModel(
    val productId: String,
    val pointId: Int,
    val pointAmount: Int,
    val imageUrl: String,
    val originalPrice: Int,
    val salePrice: Int,
    val labelColor: String?,
    val labelText: String?,
    val sort: Int,
) {

    fun toEntity() = Product(
        productId = productId,
        pointId = pointId,
        pointAmount = pointAmount,
        imageUrl = imageUrl,
        originalPrice = originalPrice,
        salePrice = salePrice,
        labelColor = labelColor,
        labelText = labelText,
        sort = sort,
    )

    companion object {
        fun from(product: Product) = ProductModel(
            productId = product.productId,
            pointId = product.pointId,
            pointAmount = product.pointAmount,
            imageUrl = product.imageUrl,
            originalPrice = product.originalPrice,
            salePrice = product.salePrice,
            labelColor = product.labelColor,
            labelText = product.labelText,
            sort = product.sort,
        )
    }
}