package site.ymango.user.v1.dto

import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.send.enums.SendTemplate
import java.time.LocalDate
import site.ymango.user.model.*
import site.ymango.user.enums.*
import java.time.LocalDateTime

data class MeResponse(
    val userId: Long,
    var phoneNumber: String,
    var email: String,
    var deviceId: String,
    val role: UserRole = UserRole.USER,
    val userProfile: MeUserProfile,
    val userAdditionalInformation: MeUserAdditionalInformation,
    val sendPushNotification: Map<PushNotification, Boolean>,
) {
    data class MeUserAdditionalInformation(
        var extraEmail: String? = null,
        val fcmToken: String,
        val fcmTokenUpdatedAt: LocalDateTime,
        val phoneBlockUsedAt: LocalDateTime? = null,
        val marketingAgreedAt: LocalDateTime? = null,
    ) {
        companion object {
            fun from(userAdditionalInformation: UserAdditionalInformationModel): MeUserAdditionalInformation {
                assert(userAdditionalInformation.fcmTokenUpdatedAt != null) { "FCM 토큰 업데이트 시간이 존재하지 않습니다." }

                return MeUserAdditionalInformation(
                    extraEmail = userAdditionalInformation.extraEmail,
                    fcmToken = userAdditionalInformation.fcmToken,
                    fcmTokenUpdatedAt = userAdditionalInformation.fcmTokenUpdatedAt!!,
                    phoneBlockUsedAt = userAdditionalInformation.phoneBlockUsedAt,
                    marketingAgreedAt = userAdditionalInformation.marketingAgreedAt,
                )
            }
        }
    }

    data class MeUserProfile(
        val userProfileId: Long,
        var userId: Long,
        var nickname: String,
        val avatarCode: String,
        var companyName: String,
        var gender: Gender,
        var birthdate: LocalDate,
        var mbti: MBTI,
        var preferredMBTI: PreferredMBTI,
        var sido: String,
        var sigungu: String,
        var location: Location,
        var height: Int,
        var bodyType: BodyType,
        var job: String,
        var smoking: Smoking,
        var drinking: Drinking,
        var religion: Religion,
        var introduction: String? = null,
        var dream: String? = null,
        var loveStyle: String? = null,
        var charm: List<String> = listOf(),
        var interest: List<String> = listOf(),
        var status: UserProfileStatus,
    ) {
        companion object {
            fun from(userProfile: UserProfileModel): MeUserProfile {
                assert(userProfile.userProfileId != null) { "사용자 프로필을 찾을 수 없습니다." }
                assert(userProfile.userId != null) { "사용자를 찾을 수 없습니다." }
                assert(userProfile.companyName != null) { "회사이름이 존재하지 않습니다." }
                assert(userProfile.status != null) { "사용자 프로필 상태가 존재하지 않습니다." }
                assert(userProfile.avatarCode != null) { "아바타 코드가 존재하지 않습니다." }

                return MeUserProfile(
                    userProfileId = userProfile.userProfileId!!,
                    userId = userProfile.userId!!,
                    nickname = userProfile.nickname,
                    avatarCode = userProfile.avatarCode!!,
                    companyName = userProfile.companyName!!,
                    gender = userProfile.gender,
                    birthdate = userProfile.birthdate,
                    mbti = userProfile.mbti,
                    preferredMBTI = userProfile.preferredMBTI,
                    sido = userProfile.sido,
                    sigungu = userProfile.sigungu,
                    location = userProfile.location,
                    height = userProfile.height,
                    bodyType = userProfile.bodyType,
                    job = userProfile.job,
                    smoking = userProfile.smoking,
                    drinking = userProfile.drinking,
                    religion = userProfile.religion,
                    introduction = userProfile.introduction,
                    dream = userProfile.dream,
                    loveStyle = userProfile.loveStyle,
                    charm = userProfile.charm,
                    interest = userProfile.interest,
                    status = userProfile.status!!,
                )
            }


        }
    }

    companion object {
        fun from(userModel: UserModel): MeResponse {
            assert(userModel.userId != null) { "사용자를 찾을 수 없습니다." }

            return MeResponse(
                userId = userModel.userId!!,
                phoneNumber = userModel.phoneNumber,
                email = userModel.email,
                deviceId = userModel.deviceId,
                role = userModel.role,
                userProfile = MeUserProfile.from(userModel.userProfile ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.")),
                userAdditionalInformation = MeUserAdditionalInformation.from(userModel.userAdditionalInformation ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 추가정보를 찾을 수 없습니다.")),
                sendPushNotification = PushNotification.entries.associateWith { userModel.userNotificationFilters.any { filter -> filter.sendTemplate == it } }
            )
        }
    }
}
