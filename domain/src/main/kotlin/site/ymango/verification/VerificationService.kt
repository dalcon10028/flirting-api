package site.ymango.verification

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.verification.enums.ReceptionType
import site.ymango.verification.model.*
import site.ymango.send.enums.*
import site.ymango.exception.*
import site.ymango.send.model.SendEvent
import site.ymango.verification.entity.Verification
import site.ymango.verification.repository.VerificationRepository
import java.time.LocalDateTime


@Service
class VerificationService(
    private val verificationRepository: VerificationRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    @Transactional(readOnly = true)
    fun isVerified(email: String, phoneNumber: String, deviceId: String, minute: Long) {
        isVerified(email, deviceId, ReceptionType.EMAIL, minute)
        isVerified(phoneNumber, deviceId, ReceptionType.SMS, minute)
    }

    /**
     * 인증정보가 존재하는지 확인한다.
     * n 분전 이후로 생성된 인증정보의 유무로 판단한다.
     */
    @Transactional(readOnly = true)
    fun isVerified(receiver: String, deviceId: String, receptionType: ReceptionType, minute: Long) {
        if (!verificationRepository.existsVerified(receiver, deviceId, receptionType, minute)) {
            throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "${receptionType.description} 인증정보가 없습니다.", mapOf("receiver" to receiver))
        }
    }

    @Transactional(readOnly = true)
    fun getVerificationCode(receiver: String, deviceId: String, receptionType: ReceptionType): VerificationModel =
        verificationRepository.findVerification(receiver, deviceId, receptionType)?.let {
            VerificationModel.from(it)
        } ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "인증정보가 없습니다.", mapOf("receiver" to receiver, "deviceId" to deviceId, "receptionType" to receptionType))

    @Transactional
    fun sendVerificationCode(
        receiver: String,
        deviceId: String,
        receptionType: ReceptionType,
        expiredAt: LocalDateTime
    ) {
        val verification = verificationRepository.save(
            Verification(
                receiver = receiver,
                deviceId = deviceId,
                code = createRandomCode(),
                receptionType = receptionType,
                expiredAt = expiredAt
            )
        )

        applicationEventPublisher.publishEvent(
            SendEvent(
                receiver = receiver,
                sendTemplate = when (receptionType) {
                    ReceptionType.EMAIL -> SendTemplate.EMAIL_VERIFICATION
                    ReceptionType.SMS -> SendTemplate.PHONE_VERIFICATION
                },
                parameters = mapOf("verificationNumber" to verification.code)
            )
        )
    }

    @Transactional
    fun verify(receiver: String, deviceId: String, receptionType: ReceptionType, code: String) =
        verificationRepository.findVerification(receiver, deviceId, receptionType)?.let {
            verificationRepository.save(it.verify(code))
        } ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "인증번호가 존재하지 않습니다.", mapOf("receiver" to receiver))

    private fun createRandomCode(): String {
        return (100000..999999).random().toString()
    }
}