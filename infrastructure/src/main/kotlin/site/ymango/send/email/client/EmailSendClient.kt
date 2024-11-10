package site.ymango.send.email.client

import feign.*
import site.ymango.send.email.dto.*

@Headers("Content-Type: application/json")
interface EmailSendClient {
    @RequestLine("POST /api/v1/mails")
    fun send(body: SendEmailRequest): SendEmailResponse
}