package site.ymango.purchase

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class PurchaseServiceTest(
    private val purchaseService: PurchaseService
) : FunSpec({

    test("should return purchase status") {
        val productId = "aa"
        val token = "panpfhnomgebnlkdnpajlmic.AO-J1OzdkamdLzbhYDv7ksb47sfQq1AI7y4KJAhdUYRCF1cB_PSAfW3cJ24QT4ZSNqkNrCF1ha2Axn1hX9sbP3Vulc8T9OkXj2AUaiAv4mmiQwpuEdbQS3k"
        val orderId = "GPA.3393-3413-3975-10071"
        val purchase = purchaseService.getReceipt(
            productId,
            token
        )

        purchase.purchaseToken shouldBe token
        purchase.orderId shouldBe orderId
    }

    test("should refund purchase") {
        val orderId = "GPA.3393-3413-3975-10071"
        purchaseService.refund(orderId)
    }

    test("should return voided purchases") {
        val startTime = LocalDateTime.now().minusDays(1)
        val endTime = LocalDateTime.now()
        purchaseService.voidedPurchases(startTime, endTime)
    }
})
