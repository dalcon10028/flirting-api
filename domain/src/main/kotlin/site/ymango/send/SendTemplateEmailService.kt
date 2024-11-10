package site.ymango.send

import org.springframework.stereotype.Service
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.send.email.model.EmailSendOptions
import site.ymango.send.enums.*
import site.ymango.send.model.SendTemplateModel

@Service
class SendTemplateEmailService: SendTemplateService {
    companion object {
        private val templateMap = mapOf(
            "EMAIL_VERIFICATION" to SendTemplateModel(
                templateCode = SendTemplate.EMAIL_VERIFICATION.name,
                category = SendCategory.INFORMATION,
                content = "",
                sendOptions = EmailSendOptions(
                    templateId = "10880",
                    parameters = emptyMap()
                )
            ),
            "EMAIL_TEST" to SendTemplateModel(
                templateCode = SendTemplate.EMAIL_TEST.name,
                category = SendCategory.INFORMATION,
                content = "",
                sendOptions = EmailSendOptions(
                    templateId = "12251",
                    parameters = emptyMap()
                )
            ),
        )
    }

    override fun getTemplate(templateCode: String): SendTemplateModel =
        templateMap[templateCode] ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "not found templateCode: $templateCode")

    override fun render(templateCode: String, template: String, parameters: Map<String, Any>): String {
        getTemplate(templateCode).let {
            val emailSendOptions = it.sendOptions as? EmailSendOptions ?: throw IllegalArgumentException("options must be EmailSendOptions")
            emailSendOptions.parameters = parameters as Map<String, String>
        }
        return ""
    }

    override fun supports(type: SendType): Boolean = type == SendType.EMAIL
}