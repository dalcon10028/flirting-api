package site.ymango

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.autoconfigure.SpringBootApplication
import site.ymango.user.service.UserServiceTest

@SpringBootApplication
class TestConfiguration : AbstractProjectConfig() {
    override val parallelism = 1
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    fun test() {
    }
}