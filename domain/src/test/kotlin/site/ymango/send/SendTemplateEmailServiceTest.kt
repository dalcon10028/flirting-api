package site.ymango.send

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.send.enums.SendTemplate

@SpringBootTest
class SendTemplateEmailServiceTest(
 private val sendTemplateEmailService: SendTemplateEmailService
) : FunSpec({

})
