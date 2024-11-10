package site.ymango.send.email

import feign.Feign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import site.ymango.send.email.client.EmailSendClient
import site.ymango.send.email.intercepter.EmailSendHeaderInjectInterceptor

@Configuration
class EmailSendClientConfig {
    @Value("\${send.email.endpoint}")
    lateinit var endpoint: String

    @Bean
    fun emailSendClient(): EmailSendClient {
        return Feign.builder()
            .encoder(JacksonEncoder())
            .decoder(JacksonDecoder())
            .requestInterceptor(emailRequestInterceptor())
            .target(EmailSendClient::class.java, endpoint)
    }

    @Bean
    fun emailRequestInterceptor(): EmailSendHeaderInjectInterceptor = EmailSendHeaderInjectInterceptor()
}