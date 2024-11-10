package site.ymango.send

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.engine.tags.TokenType
import io.mockk.every
import io.mockk.justRun
import org.mockito.Mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import site.ymango.exception.BaseException
import site.ymango.send.email.EmailSendService
import site.ymango.send.enums.SendTemplate
import site.ymango.send.model.SendEvent
import site.ymango.send.push.PushSendService
import site.ymango.send.repository.SendHistoryRepository
import site.ymango.send.repository.SendTemplatePushRepository
import site.ymango.send.repository.SendTemplateSmsRepository
import site.ymango.user.UserAdditionalInformationService
import site.ymango.user.UserService
import site.ymango.user.model.UserAdditionalInformationModel


@SpringBootTest
class SendEventListenerTest(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val sendHistoryRepository: SendHistoryRepository,
    @MockkBean private val userAdditionalInformationService: UserAdditionalInformationService,
    @MockkBean private val emailSendService: EmailSendService,
    @MockkBean private val pushSendService: PushSendService,
    @MockkBean private val sendServiceFactory: SendServiceFactory,
) : BehaviorSpec({

    beforeContainer {
        sendHistoryRepository.deleteAll()
    }

    given("SendEvent에 receiver와 userId가 둘 다 있을 때") {
        `when`("SendEvent를 발행하면") {
            then("receiver가 우선순위를 가진다.") {
                every { userAdditionalInformationService.getUserAdditionalInformation(any()) } returns UserAdditionalInformationModel(
                    fcmToken = "fcmToken",
                )
                every { sendServiceFactory.get(any()) } returns pushSendService
                justRun { pushSendService.send(any(), eq("receiver"), any(), any()) }

                applicationEventPublisher.publishEvent(
                    SendEvent(
                        receiver = "receiver",
                        userId = 1L,
                        parameters = emptyMap(),
                        sendTemplate = SendTemplate.PUSH_TEST,
                    )
                )
            }
        }
    }

    given("보조 이메일이 있는 사용자의 userId가 주어졌을 때") {
        `when`("SendEvent를 발행하면") {
            then("추가 정보에 등록한 보조이메일로 메일이 발송된다.") {
                every { sendServiceFactory.get(any()) } returns emailSendService
                justRun { emailSendService.send(eq("extraEmail"), any(), any(), any()) }
                every { userAdditionalInformationService.getUserAdditionalInformation(any()) } returns UserAdditionalInformationModel(
                    extraEmail = "extraEmail",
                    fcmToken = "fcmToken",
                )

                applicationEventPublisher.publishEvent(
                    SendEvent(
                        receiver = null,
                        userId = 1L,
                        parameters = emptyMap(),
                        sendTemplate = SendTemplate.EMAIL_TEST,
                    )
                )
            }
        }
    }
})
