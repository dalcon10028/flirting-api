package site.ymango.admin.send

import jakarta.validation.Valid
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.ymango.admin.send.dto.AdminSendRequest
import site.ymango.send.enums.SendTemplate
import site.ymango.send.model.SendEvent

@RestController
@RequestMapping("/admin/send-templates")
class AdminSendController(
    private val publisher: ApplicationEventPublisher,
) {
    @GetMapping
    fun getSendTemplates(): List<SendTemplate> {
        return SendTemplate.entries
    }

    @PostMapping("/send")
    fun send(@Valid @RequestBody body: AdminSendRequest) {
        publisher.publishEvent(SendEvent(
            userId = body.userId,
            receiver = body.receiver,
            parameters = body.parameters,
            sendTemplate = body.sendTemplate,
        ))
    }
}