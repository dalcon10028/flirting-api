package site.ymango.admin.product

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import site.ymango.admin.product.dto.CreateOrUpdateProductRequest
import site.ymango.admin.product.dto.RefundRequest
import site.ymango.product.ProductService


@Validated
@RestController
@RequestMapping("/admin/products")
class AdminProductController(
    private val productService: ProductService,
) {

    @GetMapping("/purchases")
    fun getPurchases(
        @NotNull @RequestParam userId: Long,
    ) = productService.getProductPurchases(userId)

    @PostMapping
    fun createProduct(
        @Valid @RequestBody request: CreateOrUpdateProductRequest,
    ) = productService.createProduct(request.toCreateModel())

    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable productId: String,
    ) = productService.deleteProduct(productId)


    @PostMapping("/refund")
    fun refundProduct(
        @Valid @RequestBody request: RefundRequest,
    ) = productService.refund(request.userId, request.orderId, request.productId, request.token)
}