package site.ymango.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.user.model.UserAdditionalInformationModel
import site.ymango.user.repository.UserAdditionalInformationRepository
import site.ymango.verification.VerificationService
import site.ymango.verification.enums.ReceptionType
import java.time.LocalDateTime

@Service
class UserAdditionalInformationService(
    private val userAdditionalInformationRepository: UserAdditionalInformationRepository,
    private val verificationService: VerificationService,
) {

    fun getUserAdditionalInformation(userId: Long): UserAdditionalInformationModel {
        return userAdditionalInformationRepository.findByUserId(userId)
            ?.let { UserAdditionalInformationModel.from(it) }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")
    }

    fun agreeMarketing(userId: Long): LocalDateTime {
        return userAdditionalInformationRepository.findByUserId(userId)
            ?.let {
                it.marketingAgreedAt = LocalDateTime.now()
                userAdditionalInformationRepository.save(it).marketingAgreedAt
            }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")
    }

    fun disagreeMarketing(userId: Long) {
        userAdditionalInformationRepository.findByUserId(userId)
            ?.let {
                it.marketingAgreedAt = null
                userAdditionalInformationRepository.save(it)
            }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")
    }

    @Transactional
    fun updateLastAccessedAt(userId: Long) {
        userAdditionalInformationRepository.findByUserId(userId)
            ?.let {
                it.lastAccessedAt = LocalDateTime.now()
                userAdditionalInformationRepository.save(it)
            }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")
    }

    @Transactional
    fun updateFcmToken(userId: Long, fcmToken: String) {
        userAdditionalInformationRepository.findByUserId(userId)
            ?.let {
                it.fcmToken = fcmToken
                it.fcmTokenUpdatedAt = LocalDateTime.now()
                userAdditionalInformationRepository.save(it)
            }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")
    }

    @Transactional
    fun updateExtraEmail(userId: Long, deviceId: String, extraEmail: String, code: String): UserAdditionalInformationModel {
        verificationService.verify(
            receiver = extraEmail,
            deviceId = deviceId,
            receptionType = ReceptionType.EMAIL,
            code = code
        )

        return userAdditionalInformationRepository.findByUserId(userId)
            ?.let {
                it.extraEmail = extraEmail
                UserAdditionalInformationModel.from(userAdditionalInformationRepository.save(it))
            }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")
    }

    @Transactional
    fun updatePhoneBlockUsing(userId: Long, phoneBlockUsing: Boolean): UserAdditionalInformationModel {
        return userAdditionalInformationRepository.findByUserId(userId)
            ?.let {
                it.phoneBlockUsedAt = if (phoneBlockUsing) LocalDateTime.now() else null
                UserAdditionalInformationModel.from(userAdditionalInformationRepository.save(it))
            }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")
    }
}