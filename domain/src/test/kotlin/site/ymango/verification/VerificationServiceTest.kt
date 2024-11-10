package site.ymango.verification

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.*
import io.mockk.every
import io.mockk.justRun
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.send.SendServiceFactory
import site.ymango.send.email.EmailSendService
import site.ymango.send.sms.SmsSendService
import site.ymango.verification.entity.Verification
import site.ymango.verification.enums.ReceptionType
import site.ymango.verification.repository.VerificationRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@SpringBootTest
class VerificationServiceTest(
    private val verificationService: VerificationService,
    private val verificationRepository: VerificationRepository,
    @MockkBean private val sendServiceFactory: SendServiceFactory,
    @MockkBean private val emailSendService: EmailSendService,
    @MockkBean private val smsSendService: SmsSendService,
): BehaviorSpec({
    beforeContainer {
        every { sendServiceFactory.get(any()) } returns emailSendService
        justRun { emailSendService.send(any(), any(), any(), any()) }
        justRun { smsSendService.send(any(), any(), any(), any()) }
        verificationRepository.deleteAll()
    }

    given("이메일 인증 요청을 했을 때") {
        val email = "test@test.com"
        val deviceId = "test-device-id"
        val expiredAt1 = LocalDateTime.now().plusMinutes(5)
        val expiredAt2 = LocalDateTime.now().plusMonths(1)
        `when`("이메일 인증 요청이 2개 이상 있다면") {
            verificationService.sendVerificationCode(email, deviceId, ReceptionType.EMAIL, expiredAt1)
            verificationService.sendVerificationCode(email, deviceId, ReceptionType.EMAIL, expiredAt2)
            then("가장 최근에 생성된 인증정보를 가져온다.") {
                val verification = verificationRepository.findVerification(email, deviceId, ReceptionType.EMAIL)
                verification?.code shouldNotBe null
                verification?.expiredAt?.truncatedTo(ChronoUnit.MINUTES) shouldBe expiredAt2.truncatedTo(ChronoUnit.MINUTES)
            }
        }

        `when`("이메일 인증 요청이 없다면") {
            then("인증정보가 없다는 예외를 던진다.") {
                shouldThrow<BaseException> {
                    verificationService.getVerificationCode(email, deviceId, ReceptionType.EMAIL)
                }.code shouldBe ErrorCode.RESOURCE_NOT_FOUND
            }
        }

        `when`("이메일 인증 요청이 있다면") {
            verificationRepository.save(
                Verification(
                    receiver = email,
                    deviceId = deviceId,
                    code = "test-code",
                    receptionType = ReceptionType.EMAIL,
                    expiredAt = expiredAt1
                )
            )
            then("인증정보를 가져온다.") {
                verificationService.sendVerificationCode(email, deviceId, ReceptionType.EMAIL, expiredAt1)
                val verification = verificationRepository.findVerification(email, deviceId, ReceptionType.EMAIL)
                verification?.code shouldNotBe null
                verification?.expiredAt?.truncatedTo(ChronoUnit.MINUTES) shouldBe expiredAt1.truncatedTo(ChronoUnit.MINUTES)
            }
        }

        `when`("인증 번호가 틀리면") {
            verificationService.sendVerificationCode(email, deviceId, ReceptionType.EMAIL, expiredAt1)
            val code = verificationRepository.findVerification(email, deviceId, ReceptionType.EMAIL)?.code
            then("인증번호가 일치하지 않습니다.는 예외를 던진다.") {
                shouldThrow<BaseException> {
                    verificationService.verify(email, deviceId, ReceptionType.EMAIL, "1234")
                }.message shouldBe "인증번호가 일치하지 않습니다."
            }
        }
    }

    given("이메일 인증 요청을 완료하였을 때") {
        val email = "test@test.com"
        val deviceId = "test-device-id"
        val expiredAt1 = LocalDateTime.now().plusMinutes(5)
        `when`("중복으로 인증 완료 요청을 한다면") {
            then("인증번호가 존재하지 않습니다.") {
                verificationService.sendVerificationCode(email, deviceId, ReceptionType.EMAIL, expiredAt1)
                val verification = verificationRepository.findVerification(email, deviceId, ReceptionType.EMAIL)
                verification?.code shouldNotBe null
                verificationService.verify(email, deviceId, ReceptionType.EMAIL, verification?.code!!)
                shouldThrow<BaseException> {
                    verificationService.verify(email, deviceId, ReceptionType.EMAIL, verification?.code!!)
                }.code shouldBe ErrorCode.RESOURCE_NOT_FOUND
            }
        }

        `when`("만료기한 후라면") {
            then("인증정보가 없다는 예외를 던진다.") {
                verificationService.sendVerificationCode(email, deviceId, ReceptionType.EMAIL, LocalDateTime.now().minusMinutes(1))
                shouldThrow<BaseException> {
                    verificationService.verify(email, deviceId, ReceptionType.EMAIL, "1234")
                }.code shouldBe ErrorCode.RESOURCE_NOT_FOUND

            }
        }
    }
})