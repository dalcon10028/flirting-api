package site.ymango.send

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.send.entity.SendTemplatePush
import site.ymango.send.enums.SendCategory
import site.ymango.send.repository.SendTemplatePushRepository

@SpringBootTest
class SendTemplatePushServiceTest(
    private val sendTemplatePushService: SendTemplatePushService,
    private val sendTemplatePushRepository: SendTemplatePushRepository
) : FunSpec({

    beforeContainer {
        sendTemplatePushRepository.deleteAll()
    }

    test("getTemplate") {
        sendTemplatePushRepository.save(SendTemplatePush("templateCode", "templateName", SendCategory.INFORMATION, "templateContent"))
        val template = sendTemplatePushService.getTemplate("templateCode")
        template.templateCode shouldBe "templateCode"
        template.category shouldBe SendCategory.INFORMATION
        template.title shouldBe "templateName"
    }
})
