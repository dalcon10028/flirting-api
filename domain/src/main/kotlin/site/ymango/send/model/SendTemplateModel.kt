package site.ymango.send.model

import site.ymango.send.enums.SendCategory
import site.ymango.send.entity.*

data class SendTemplateModel (
    val templateCode: String,
    val category: SendCategory,
    val title: String? = null,
    val content: String,
    val sendOptions: SendOptions? = null,
) {
    companion object {
        fun from(sendTemplate: SendTemplatePush): SendTemplateModel =
            SendTemplateModel(
                templateCode = sendTemplate.templateCode,
                category = sendTemplate.category,
                title = sendTemplate.title,
                content = sendTemplate.content,
            )

        fun from(sendTemplateSms: SendTemplateSms): SendTemplateModel =
            SendTemplateModel(
                templateCode = sendTemplateSms.templateCode,
                category = sendTemplateSms.category,
                content = sendTemplateSms.content,
            )
    }
}