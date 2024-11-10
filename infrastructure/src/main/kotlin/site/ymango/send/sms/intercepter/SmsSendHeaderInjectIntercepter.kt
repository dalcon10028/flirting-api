package site.ymango.send.sms.intercepter

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class SmsSendHeaderInjectIntercepter : RequestInterceptor {

    @Value("\${send.sms.access-key}")
    lateinit var accessKey: String

    @Value("\${send.sms.secret-key}")
    lateinit var secretKey: String

    override fun apply(template: RequestTemplate) {
        val timestamp = System.currentTimeMillis().toString()
        template.header("x-ncp-apigw-timestamp", timestamp)
        template.header("x-ncp-iam-access-key", accessKey)
        template.header("x-ncp-apigw-signature-v2", makeSign(template, timestamp))
    }

    private fun makeSign(template: RequestTemplate, timestamp: String): String {
        val method = template.method() // method
        val url = template.url() // url (include query string)
        val message = "$method $url\n$timestamp\n$accessKey"

        val signingKey = secretKey.toByteArray(Charsets.UTF_8)
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(signingKey, "HmacSHA256"))
        val rawHmac = mac.doFinal(message.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(rawHmac)
    }
}