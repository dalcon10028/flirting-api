package site.ymango.send

import org.springframework.stereotype.Component
import site.ymango.send.enums.SendType


@Component
class SendTemplateFactory(
    private val sendTemplateServices: List<SendTemplateService>
) {
    fun get(type: SendType): SendTemplateService = sendTemplateServices.first { it.supports(type) }
}