package site.ymango.config

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.MustacheFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TemplateEngineConfiguration {
    @Bean
    fun templateEngine(): MustacheFactory = DefaultMustacheFactory()
}