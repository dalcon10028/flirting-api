package site.ymango.send

import site.ymango.send.enums.SendType
import site.ymango.send.model.SendTemplateModel
interface SendTemplateService {
    fun getTemplate(templateCode: String): SendTemplateModel

    fun render(templateCode: String, template: String, parameters: Map<String, Any>): String

    fun supports(type: SendType): Boolean
}