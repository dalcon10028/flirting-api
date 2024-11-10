package site.ymango.product

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.point.PointService
import site.ymango.point.enums.PointType
import site.ymango.point.model.PointWalletModel
import site.ymango.product.entity.Product
import site.ymango.product.entity.ProductPurchase
import site.ymango.product.model.CreateProductModel
import site.ymango.product.model.ProductModel
import site.ymango.product.model.ProductPurchaseModel
import site.ymango.product.repository.ProductPurchaseRepository
import site.ymango.product.repository.ProductRepository
import site.ymango.purchase.PurchaseService
import site.ymango.purchase.enums.PurchaseState

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productPurchaseRepository: ProductPurchaseRepository,
    private val purchaseService: PurchaseService,
    private val pointService: PointService,
) {

    @Transactional(readOnly = true)
    fun getProducts(): List<ProductModel> = productRepository.findAllByDeletedAtIsNull().map { ProductModel.from(it) }

    @Transactional(readOnly = true)
    fun getProductPurchases(uid: Long): List<ProductPurchaseModel> =
        productPurchaseRepository.findAllByUserId(uid).map { ProductPurchaseModel.from(it) }

    @Transactional
    fun createProduct(product: CreateProductModel): ProductModel {
        if (productRepository.existsByProductId(product.productId)) {
            throw BaseException(ErrorCode.DUPLICATE_RESOURCE, "이미 존재하는 상품입니다.")
        }

        val pointPolicy = pointService.getPointPolicy(PointType.PURCHASE)

        assert(pointPolicy.pointId != null)

        return ProductModel.from(productRepository.save(Product(
            productId = product.productId,
            pointId = pointPolicy.pointId!!,
            pointAmount = product.pointAmount,
            imageUrl = product.imageUrl,
            originalPrice = product.originalPrice,
            salePrice = product.salePrice,
            labelColor = product.labelColor,
            labelText = product.labelText,
            sort = product.sort,
        )))
    }

    @Transactional
    fun deleteProduct(productId: String) {
        val product = productRepository.findByProductIdAndDeletedAtIsNotNull(productId) ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 상품입니다.")
        productRepository.delete(product)
    }

    @Transactional
    fun purchase(uid: Long, productId: String, token: String): PointWalletModel {
        if (productPurchaseRepository.existsByUserIdAndToken(uid, token)) {
            throw BaseException(ErrorCode.DUPLICATE_RESOURCE, "이미 처리된 구매 요청입니다.")
        }

        val product = productRepository.findById(productId).orElseThrow { throw BaseException(ErrorCode.PRODUCT_NOT_FOUND) }

        val receipt = purchaseService.getReceipt(productId, token)

        if (receipt.purchaseState == PurchaseState.CANCELLED) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "취소된 구매입니다.")
        } else if (receipt.purchaseState == PurchaseState.PENDING) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "환불된 구매입니다.")
        }

        productPurchaseRepository.save(ProductPurchase(
            userId = uid,
            productId = product.productId,
            pointId = product.pointId,
            pointAmount = product.pointAmount,
            token = receipt.purchaseToken,
            orderId = receipt.orderId,
            state = receipt.purchaseState,
            purchasedAt = receipt.purchaseTime,
            response = receipt.response,
        ))

        return pointService.applyPoint(uid, PointType.PURCHASE, product.pointAmount)
    }

    @Transactional
    fun refund(userId: Long, orderId: String, productId: String, token: String): ProductPurchaseModel {
        val productPurchase =
            productPurchaseRepository.findByOrderId(orderId) ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "구매 내역이 존재하지 않습니다.")

        if (productPurchase.state == PurchaseState.CANCELLED) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "이미 취소된 구매입니다.")
        }

        pointService.applyPoint(userId, PointType.REFUND, -productPurchase.pointAmount)
        purchaseService.refund(orderId)
        val receipt = purchaseService.getReceipt(productId, token)
        return productPurchaseRepository.save(productPurchase.refund(receipt.response)).let { ProductPurchaseModel.from(it) }
    }
}