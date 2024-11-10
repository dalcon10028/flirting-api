package site.ymango.send.sms.client

import feign.*
import site.ymango.send.sms.dto.*

@Headers("Content-Type: application/json")
interface SmsSendClient {
    @RequestLine("POST /services/{serviceId}/messages")
    fun send(body: SendSmsRequest): SendSmsResponse

}