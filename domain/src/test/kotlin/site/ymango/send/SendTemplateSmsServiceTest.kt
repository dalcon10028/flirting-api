package site.ymango.send

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SendTemplateSmsServiceTest(
      private val service: SendTemplateSmsService,

) : FunSpec({

    test("render") {
        val result = service.render(
            templateCode = "templateCode",
            template = "인증번호는 {{verificationNumber}}입니다",
            parameters = mapOf("verificationNumber" to "123456"),
        )
        result shouldBe "인증번호는 123456입니다"
    }
})
