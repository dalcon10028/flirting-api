package site.ymango.send

import com.github.mustachejava.MustacheFactory
import org.springframework.stereotype.Service
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.send.enums.SendType
import site.ymango.send.model.SendTemplateModel
import site.ymango.send.repository.SendTemplateSmsRepository
import java.io.StringReader
import java.io.StringWriter
import java.util.*

@Service
class SendTemplateSmsService(
    private val sendTemplateSmsRepository: SendTemplateSmsRepository,
    private val templateEngine: MustacheFactory,
) : SendTemplateService {
    override fun getTemplate(templateCode: String): SendTemplateModel =
        sendTemplateSmsRepository.findById(templateCode)
            .orElseThrow { throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "not found templateCode: $templateCode") }
            .let { SendTemplateModel.from(it) }

    override fun render(templateCode: String, template: String, parameters: Map<String, Any>): String =
        StringWriter().also {
            // TODO: cache compiled template
            templateEngine.compile(StringReader(template), templateCode)
                .execute(it, parameters)
                .flush()
        }.toString()

    override fun supports(type: SendType): Boolean = type == SendType.SMS
}