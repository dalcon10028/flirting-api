package site.ymango.product.dto

import site.ymango.product.model.ProductModel

data class ProductResponse(
    val productId: String,
    val pointAmount: Int,
    val imageUrl: String,
    val originalPrice: Int,
    val salePrice: Int,
    val labelColor: String?,
    val labelText: String?,
    val sort: Int,
) {
    companion object {
        fun from(product: ProductModel) = ProductResponse(
            productId = product.productId,
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
