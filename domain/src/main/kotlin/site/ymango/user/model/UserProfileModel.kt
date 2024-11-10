package site.ymango.user.model

import site.ymango.user.entity.UserProfile
import site.ymango.user.enums.*
import java.time.LocalDate
import java.time.LocalDateTime

data class UserProfileModel(
    val userProfileId: Long? = null,
    var userId: Long? = null,
    var nickname: String,
    var avatarCode: String? = null,
    var companyName: String? = null,
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
    var status: UserProfileStatus? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var user: UserModel? = null,
) {
    companion object {
        fun from(userProfile: UserProfile): UserProfileModel =
            UserProfileModel(
                userProfileId = userProfile.userProfileId,
                userId = userProfile.userId,
                nickname = userProfile.nickname,
                avatarCode = userProfile.avatarCode,
                companyName = userProfile.companyName,
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
                status = userProfile.status,
                createdAt = userProfile.createdAt,
                updatedAt = userProfile.updatedAt,
                user = userProfile.user?.let { UserModel.from(it) }
            )
    }

    fun toEntity(): UserProfile {
        assert(avatarCode != null) { "avatarCode must not be null" }

        return UserProfile(
            userProfileId = userProfileId,
            avatarCode = avatarCode!!,
            userId = userId,
            nickname = nickname,
            companyName = companyName,
            gender = gender,
            birthdate = birthdate,
            mbti = mbti,
            preferredMBTI = preferredMBTI,
            sido = sido,
            sigungu = sigungu,
            location = location,
            height = height,
            bodyType = bodyType,
            job = job,
            smoking = smoking,
            drinking = drinking,
            religion = religion,
            introduction = introduction,
            dream = dream,
            loveStyle = loveStyle,
            charm = charm,
            interest = interest,
        )
    }
}
