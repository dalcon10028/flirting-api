package site.ymango.send.email.model

import site.ymango.send.model.SendOptions

data class EmailSendOptions(
    val templateId: String,
    var parameters: Map<String, String>,
) : SendOptions()