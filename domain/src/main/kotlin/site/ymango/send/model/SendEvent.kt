package site.ymango.send.model

import site.ymango.send.enums.SendTemplate

data class SendEvent (
    val userId: Long? = null,
    val receiver: String? = null,
    val parameters: Map<String, Any>,
    val sendTemplate: SendTemplate,
) {
    constructor(userId: Long, parameters: Map<String, Any>, sendTemplate: SendTemplate) : this(userId, null, parameters, sendTemplate)
    constructor(receiver: String, parameters: Map<String, Any>, sendTemplate: SendTemplate) : this(null, receiver, parameters, sendTemplate)
}