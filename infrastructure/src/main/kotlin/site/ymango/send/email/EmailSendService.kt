package site.ymango.send.email

import org.springframework.stereotype.Service
import site.ymango.send.SendService
import site.ymango.send.email.client.EmailSendClient
import site.ymango.send.email.dto.SendEmailRequest
import site.ymango.send.email.model.EmailSendOptions
import site.ymango.send.email.model.Recipient
import site.ymango.send.enums.*
import site.ymango.send.model.SendOptions

@Service
class EmailSendService(
    private val emailSendClient: EmailSendClient,
) : SendService {
    companion object {
        const val SENDER_NAME = "플러팅"
    }

    override fun send(receiver: String, title: String?, content: String, sendOptions: SendOptions?): Map<String, Any> {
        val options: EmailSendOptions = sendOptions as? EmailSendOptions ?: throw IllegalArgumentException("options must be EmailSendOptions")
        val request = SendEmailRequest(
            senderName = SENDER_NAME,
            templateSid = options.templateId,
            recipients = listOf(Recipient(address = receiver, parameters = options.parameters)),
        )


        return emailSendClient.send(request).let {
            mapOf(
                "requestId" to it.requestId.toString(),
                "count" to it.count.toString()
            )
        }
    }

    override fun supports(type: SendType): Boolean = type == SendType.EMAIL
}