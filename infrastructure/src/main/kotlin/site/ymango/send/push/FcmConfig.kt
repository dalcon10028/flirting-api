package site.ymango.send.push

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FcmConfig {
    @Value("\${send.push.key-file}")
    lateinit var keyFile: String

    @Bean
    fun fcmClient(): FirebaseMessaging {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(ClassPathResource(keyFile).inputStream))
            .build()
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
        return FirebaseMessaging.getInstance()
    }
}