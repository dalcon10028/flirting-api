package site.ymango.verification.v1

import org.springframework.web.bind.annotation.*
import site.ymango.common.annotation.AppHeader
import site.ymango.common.model.FlirtingHeader
import site.ymango.verification.VerificationService
import site.ymango.verification.enums.ReceptionType
import site.ymango.verification.v1.dto.VerificationResponse
import java.time.LocalDateTime


@RestController
@RequestMapping("/v1/verifications")
class VerificationController(
    private val verificationService: VerificationService
) {
    @GetMapping
    fun getVerificationCode(
        @AppHeader header: FlirtingHeader,
        @RequestParam receiver: String,
        @RequestParam receptionType: ReceptionType,
    ) = VerificationResponse.of(verificationService.getVerificationCode(receiver, header.deviceId, receptionType))

    @PostMapping("/email")
    fun sendEmailVerificationCode(
        @AppHeader header: FlirtingHeader,
        @RequestParam receiver: String,
    ): Map<String, Boolean> {
        verificationService.sendVerificationCode(receiver, header.deviceId, ReceptionType.EMAIL, LocalDateTime.now().plusMinutes(5))
        return mapOf("success" to true)
    }

    @PostMapping("/email/verify")
    fun verifyEmail(
        @AppHeader header: FlirtingHeader,
        @RequestParam receiver: String,
        @RequestParam code: String,
    ): Map<String, Boolean> {
        verificationService.verify(receiver, header.deviceId, ReceptionType.EMAIL, code)
        return mapOf("success" to true)
    }

    @PostMapping("/phone")
    fun sendPhoneVerificationCode(
        @AppHeader header: FlirtingHeader,
        @RequestParam receiver: String,
    ): Map<String, Boolean> {
        verificationService.sendVerificationCode(receiver, header.deviceId, ReceptionType.SMS, LocalDateTime.now().plusMinutes(5))
        return mapOf("success" to true)
    }

    @PostMapping("/phone/verify")
    fun verifyPhone(
        @AppHeader header: FlirtingHeader,
        @RequestParam receiver: String,
        @RequestParam code: String,
    ): Map<String, Boolean> {
        verificationService.verify(receiver, header.deviceId, ReceptionType.SMS, code)
        return mapOf("success" to true)
    }
}