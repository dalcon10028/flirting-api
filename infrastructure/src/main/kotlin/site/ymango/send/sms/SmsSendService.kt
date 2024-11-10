package site.ymango.send.sms

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import site.ymango.send.SendService
import site.ymango.send.enums.*
import site.ymango.send.model.SendOptions
import site.ymango.send.sms.client.SmsSendClient

private val logger = KotlinLogging.logger {}

@Service
class SmsSendService(
    private val smsSendClient: SmsSendClient
) : SendService {
    override fun send(receiver: String, title: String?, content: String, sendOptions: SendOptions?): Map<String, Any> = emptyMap()

    override fun supports(type: SendType): Boolean = type == SendType.SMS
}