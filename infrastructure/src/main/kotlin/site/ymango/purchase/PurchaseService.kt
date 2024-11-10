package site.ymango.purchase

import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.model.ProductPurchase
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.purchase.enums.PurchaseState
import site.ymango.purchase.model.Receipt
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

private val logger = KotlinLogging.logger {}

@Service
class PurchaseService(
    private val androidPublisher: AndroidPublisher
) {
    companion object {
        private const val PACKAGE_NAME = "me.whymango.flirting_app"
    }

    fun getReceipt(productId: String, token: String): Receipt =
        try {
            val response: ProductPurchase = androidPublisher.purchases()
                .products()
                .get(PACKAGE_NAME, productId, token)
                .execute()

            val purchaseTime = Instant.ofEpochMilli(response.purchaseTimeMillis).let {
                LocalDateTime.ofInstant(it, ZoneId.systemDefault())
            }

            Receipt(
                productId = productId,
                orderId = response.orderId,
                purchaseToken = token,
                purchaseTime = purchaseTime,
                purchaseState = PurchaseState.fromAndroidValue(response.purchaseState),
                response = response
            )
        } catch (e: Exception) {
            logger.error(e) { "Failed to get purchase" }
            throw BaseException(ErrorCode.FAIL_TO_GET_PURCHASE)
        }

    fun refund(orderId: String) {
        androidPublisher.orders().refund(PACKAGE_NAME, orderId).execute()
    }


    // TODO: Implement this method
    @Suppress("unused")
    fun voidedPurchases(startTime: LocalDateTime, endTime:LocalDateTime) {
        val startTimeStamp = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endTimeStamp = endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val response = androidPublisher.purchases().voidedpurchases().list(PACKAGE_NAME)
//            .setStartTime(startTimeStamp)
//            .setEndTime(endTimeStamp)
            .execute()

        println(response)
    }
}