package site.ymango.send.push

import com.google.firebase.messaging.*
import org.springframework.stereotype.Service
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.send.SendService
import site.ymango.send.enums.*
import site.ymango.send.model.SendOptions

@Service
class PushSendService(
    private val fcmClient: FirebaseMessaging,
) : SendService {
    override fun send(receiver: String, title: String?, content: String, sendOptions: SendOptions?): Map<String, Any> {

        val notification = Notification.builder()
            .setTitle(title)
            .setBody(content)
            .build()

        val message = Message.builder()
            .setNotification(notification)
            .setToken(receiver)
            .build()


        val result = fcmClient.send(message)
        return mapOf("result" to result)
    }

    override fun supports(type: SendType): Boolean = type == SendType.PUSH
}