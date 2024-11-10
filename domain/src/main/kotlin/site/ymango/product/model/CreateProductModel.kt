package site.ymango.product.model

data class CreateProductModel(
    val productId: String,
    val pointAmount: Int,
    val imageUrl: String,
    val originalPrice: Int,
    val salePrice: Int,
    val labelColor: String?,
    val labelText: String?,
    val sort: Int,
)
