package site.ymango.product

import org.springframework.web.bind.annotation.*
import site.ymango.auth.model.UserInfo
import site.ymango.common.annotation.Auth
import site.ymango.point.v1.dto.PointWalletResponse
import site.ymango.product.dto.*


@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productService: ProductService,
) {

    @GetMapping
    fun getProducts() = productService.getProducts().map { ProductResponse.from(it) }

    @PostMapping("/purchase")
    fun purchaseProduct(
        @Auth user: UserInfo,
        @RequestBody request: PurchaseRequest,
    ) = productService.purchase(user.userId, request.productId, request.token)
        .let { PointWalletResponse.from(it) }
}