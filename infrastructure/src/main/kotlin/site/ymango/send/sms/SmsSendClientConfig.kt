package site.ymango.send.sms

import feign.Feign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import site.ymango.send.sms.client.SmsSendClient
import site.ymango.send.sms.intercepter.SmsSendHeaderInjectIntercepter

@Configuration
class SmsSendClientConfig {
    @Value("\${send.sms.endpoint}")
    lateinit var endpoint: String

    @Bean
    fun smsSendClient(): SmsSendClient {
        return Feign.builder()
            .encoder(JacksonEncoder())
            .decoder(JacksonDecoder())
            .requestInterceptor(smsRequestInterceptor())
            .target(SmsSendClient::class.java, endpoint)
    }

    @Bean
    fun smsRequestInterceptor(): SmsSendHeaderInjectIntercepter = SmsSendHeaderInjectIntercepter()
}