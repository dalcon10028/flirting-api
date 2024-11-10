package site.ymango.admin.product.dto

import site.ymango.product.model.CreateProductModel

data class CreateOrUpdateProductRequest(
    val productId: String?,
    val pointAmount: Int?,
    val imageUrl: String?,
    val originalPrice: Int?,
    val salePrice: Int?,
    val labelColor: String?,
    val labelText: String?,
    val sort: Int?,
) {
    fun toCreateModel(): CreateProductModel {
        assert(productId != null)
        assert(pointAmount != null)
        assert(imageUrl != null)
        assert(originalPrice != null)
        assert(salePrice != null)
        assert(sort != null)
        return CreateProductModel(
            productId = productId!!,
            pointAmount = pointAmount!!,
            imageUrl = imageUrl!!,
            originalPrice = originalPrice!!,
            salePrice = salePrice!!,
            labelColor = labelColor,
            labelText = labelText,
            sort = sort!!,
        )
    }
}