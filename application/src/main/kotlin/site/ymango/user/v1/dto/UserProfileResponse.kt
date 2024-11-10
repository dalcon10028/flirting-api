package site.ymango.user.v1.dto

import site.ymango.user.enums.*
import site.ymango.user.model.UserProfileModel
import java.time.LocalDate

data class UserProfileResponse(
    val userProfileId: Long? = null,
    var userId: Long? = null,
    var nickname: String,
    val avatarCode: String,
    var companyName: String? = null,
    var gender: Gender,
    var birthdate: LocalDate,
    var mbti: MBTI,
    var preferredMBTI: PreferredMBTI,
    var sido: String,
    var sigungu: String,
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
) {
    companion object {
        fun from(userProfile: UserProfileModel) : UserProfileResponse {
            assert(userProfile.avatarCode != null)

            return UserProfileResponse(
                userProfileId = userProfile.userProfileId,
                userId = userProfile.userId,
                nickname = userProfile.nickname,
                avatarCode = userProfile.avatarCode!!,
                companyName = userProfile.companyName,
                gender = userProfile.gender,
                birthdate = userProfile.birthdate,
                mbti = userProfile.mbti,
                preferredMBTI = userProfile.preferredMBTI,
                sido = userProfile.sido,
                sigungu = userProfile.sigungu,
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
            )
        }
    }
}
