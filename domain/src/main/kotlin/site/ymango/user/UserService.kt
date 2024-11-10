package site.ymango.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.company.CompanyService
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.point.PointService
import site.ymango.point.enums.PointType
import site.ymango.user.entity.UserAdditionalInformation
import site.ymango.user.entity.WithdrawalUser
import site.ymango.user.repository.*
import site.ymango.user.model.*
import site.ymango.verification.VerificationService
import java.time.LocalDate

@Service
class UserService(
    private val userProfileService: UserProfileService,
    private val companyService: CompanyService,
    private val verificationService: VerificationService,
    private val pointService: PointService,
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository,
    private val userAdditionalInformationRepository: UserAdditionalInformationRepository,
    private val withdrawalUserRepository: WithdrawalUserRepository,
) {

    /**
     * 사용자 조회, deviceId가 바뀐경우 업데이트
     */
    @Transactional
    fun findUser(phoneNumber: String, deviceId: String): UserModel {
        val user = userRepository.findByPhoneNumber(phoneNumber)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "회원정보가 없습니다.")
        if (user.deviceId != deviceId) {
            user.deviceId = deviceId
            userRepository.save(user)
        }
        return UserModel.from(user)
    }

    @Transactional(readOnly = true)
    fun findByUserId(userId: Long): UserModel {
        return UserModel.of(
            userRepository.findById(userId)
                .orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다.") },
            userProfileRepository.findByUserId(userId)
                ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다."),
            userAdditionalInformationRepository.findByUserId(userId)
                ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")
        )
    }

    @Transactional(readOnly = true)
    fun findByUserIdOrNull(userId: Long): UserModel? {
        return UserModel.ofNullable(userRepository.findById(userId).orElse(null))
    }

    @Transactional(readOnly = true)
    fun findByDeviceId(deviceId: String): UserModel =
        userRepository.findByDeviceId(deviceId)
            .let { it?.toModel() }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다.")


    @Transactional
    fun registerUser(userModel: UserModel, userProfileModel: UserProfileModel, fcmToken: String): UserModel {

        userProfileModel.birthdate.checkMinor()
        // 탈퇴 회원인지 확인
        if (withdrawalUserRepository.existsByPhoneNumberOrDeviceId(
                userModel.phoneNumber,
                userModel.deviceId
            )) {
            throw BaseException(ErrorCode.WITHDRAWAL_USER, "탈퇴한 회원입니다.")
        }

        // 40 분 이내에 인증된 사용자인지 확인한다.
        verificationService.isVerified(userModel.email, userModel.phoneNumber, userModel.deviceId, 40)

        // 프로필 아바타 코드 생성
        userProfileService.generateProfileAvatarCode(userProfileModel.gender).let {
            userProfileModel.avatarCode = it
        }

        if (userRepository.existsByPhoneNumber(userModel.phoneNumber)) {
            throw BaseException(ErrorCode.DUPLICATE_PHONE, "이미 가입된 전화번호입니다.")
        }

        val user = userRepository.save(userModel.toEntity())

        userProfileModel.userId = user.userId
        userProfileModel.companyName = companyService.getCompanyName(userModel.email.split("@").last())

        assert(user.userId != null) { "사용자 아이디가 존재하지 않습니다." }
        // 포인트 지갑 생성
        pointService.createPointWallet(user.userId!!)

        // 웰컴 포인트 지급
        pointService.applyPoint(user.userId, PointType.SIGN_UP)

        userProfileRepository.save(userProfileModel.toEntity())
        userAdditionalInformationRepository.save(UserAdditionalInformation(
                userId = user.userId,
                fcmToken = fcmToken
            ))
        return user.toModel()
    }

    /**
     * 회원 탈퇴
     */
    fun withdrawalUser(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다.") }
        val userProfile = userProfileRepository.findByUserId(userId) ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.")

        withdrawalUserRepository.save(WithdrawalUser(
            userId = userId,
            phoneNumber = user.phoneNumber,
            email = user.email,
            deviceId = user.deviceId
        ))

        assert(userProfile.userProfileId != null) { "사용자 프로필 아이디가 존재하지 않습니다." }

        userRepository.deleteById(userId)
        userProfileRepository.save(userProfile.withdrawal())
    }

    private fun LocalDate.checkMinor() {
        val now = LocalDate.now()
        val age = now.year - this.year
        if (age < 18) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "만 18세 미만은 가입할 수 없습니다.")
        }
    }
}