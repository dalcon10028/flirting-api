package site.ymango

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TestConfig: AbstractProjectConfig() {
    override fun extensions() = listOf<Extension>()
}