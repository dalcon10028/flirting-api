package site.ymango.config

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

class SecurityConfigTest: BehaviorSpec({
    given("passwordEncoder") {
        val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        `when`("아무 설정하지 않으면") {
            then("passwordEncoder는 BCryptPasswordEncoder를 사용한다.") {
                val encodedPassword = passwordEncoder.encode("password")
                println(encodedPassword)
                encodedPassword.startsWith("{bcrypt}") shouldBe true
            }
        }

        `when`("noop으로 설정된 패스워드가 있으면") {
            then("passwordEncoder는 NoOpPasswordEncoder를 사용한다.") {
                passwordEncoder.matches("password", "{noop}password") shouldBe true
            }
        }

        `when`("인코더 접두사가 없는 인코딩된 비밀번호는") {
            then("IllegalArgumentException이 발생한다.") {
                shouldThrow<IllegalArgumentException> {
                    passwordEncoder.matches("password", "password")
                }
            }
        }
    }
})