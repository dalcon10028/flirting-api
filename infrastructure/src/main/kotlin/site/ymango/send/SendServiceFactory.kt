package site.ymango.send

import org.springframework.stereotype.Component
import site.ymango.send.enums.SendType

@Component
class SendServiceFactory(
    private val sendServices: List<SendService>
) {
    fun get(type: SendType): SendService = sendServices.first { it.supports(type) }
}