package site.ymango.send.sms

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.send.enums.SendTemplate

@SpringBootTest
class SmsSendServiceTest(
    private val smsSendService: SmsSendService
) : FunSpec({
    test("send sms") {

    }
})