package site.ymango.send

import site.ymango.send.enums.*
import site.ymango.send.model.SendOptions

interface SendService {
    fun send(receiver: String, title: String?, content: String, sendOptions: SendOptions?): Map<String, Any>

    fun supports(type: SendType): Boolean
}