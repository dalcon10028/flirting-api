package site.ymango.config

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisherScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class GoogleCredentialsConfig {
    @Value("\${google.credentials.path}")
    lateinit var credentialsPath: String

    @Value("\${google.credentials.projectId}")
    lateinit var projectId: String

    @Bean
    fun androidPublisher(): AndroidPublisher {
        val inputStream = ClassPathResource(credentialsPath).inputStream

        val credentials = GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf(AndroidPublisherScopes.ANDROIDPUBLISHER))
        return AndroidPublisher.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            HttpCredentialsAdapter(credentials)
        )
            .setApplicationName(projectId)
            .build()
    }
}