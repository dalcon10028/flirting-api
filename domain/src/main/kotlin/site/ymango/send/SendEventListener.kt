package site.ymango.send

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import site.ymango.send.entity.SendHistory
import site.ymango.send.enums.SendType
import site.ymango.send.model.SendEvent
import site.ymango.send.repository.SendHistoryRepository
import site.ymango.user.UserAdditionalInformationService
import site.ymango.user.UserService

@Component
class SendEventListener(
    private val userService: UserService,
    private val userAdditionalInformationService: UserAdditionalInformationService,
    private val sendServiceFactory: SendServiceFactory,
    private val sendTemplateFactory: SendTemplateFactory,
    private val sendHistoryRepository: SendHistoryRepository,
) {
    private val logger = KotlinLogging.logger {}

    @EventListener(value = [SendEvent::class])
    fun send(sendEvent: SendEvent) {
        logger.info { "receive sendEvent: $sendEvent" }

        val sendTemplateService = sendTemplateFactory.get(sendEvent.sendTemplate.sendType)
        val sendService = sendServiceFactory.get(sendEvent.sendTemplate.sendType)

        // STEP1: 수신자 정보 가져오기
        val receiver = getReceiver(sendEvent)

        // STEP2: 템플릿 가져오기
        val (templateCode, category, title, content, sendOptions) = sendTemplateService.getTemplate(sendEvent.sendTemplate.name)

        // STEP3: 템플릿 렌더링
        // TODO: auto commit not working
        val sendHistory = sendHistoryRepository.save(
            SendHistory(
                userId = sendEvent.userId,
                sendType = sendEvent.sendTemplate.sendType,
                category = category,
                receiver = receiver,
                templateCode = templateCode,
                title = title,
                content = content,
                parameters = sendEvent.parameters,
            )
        )
        val renderedContent = sendTemplateService.render(templateCode, content, sendEvent.parameters)
        sendHistoryRepository.save(
            sendHistory.rendered(renderedContent)
        )

        // STEP4: 발송
        try {
            sendService.send(
                receiver = receiver,
                title = title,
                content = renderedContent,
                sendOptions = sendOptions
            ).let {
                assert(sendHistory.sendHistoryId != null)
                sendHistoryRepository.save(
                    sendHistory.delivered(it)
                )
            }
        } catch (e: Exception) {
            logger.error(e) { "failed to send message" }
            sendHistoryRepository.save(
                sendHistory.failed(e.message)
            )
            return
        }

        logger.info { "complete sendEvent: $sendEvent" }
    }

    /**
     * 수신자 정보 가져오기
     */
    private fun getReceiver(sendEvent: SendEvent): String {
        // 둘 중 하나는 있어야함
        assert(sendEvent.userId != null || sendEvent.receiver != null)

        return sendEvent.receiver ?: when (sendEvent.sendTemplate.sendType) {
            SendType.EMAIL ->
                userAdditionalInformationService.getUserAdditionalInformation(sendEvent.userId!!).extraEmail ?:
                userService.findByUserId(sendEvent.userId).email
            SendType.SMS -> userService.findByUserId(sendEvent.userId!!).phoneNumber
            SendType.PUSH -> userAdditionalInformationService.getUserAdditionalInformation(sendEvent.userId!!).fcmToken
            else -> throw IllegalArgumentException("not supported sendType: ${sendEvent.sendTemplate.sendType}")
        }
    }
}