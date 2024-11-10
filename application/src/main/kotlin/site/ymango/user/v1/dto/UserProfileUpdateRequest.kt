package site.ymango.user.v1.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.Length
import site.ymango.user.enums.*
import site.ymango.user.model.Location
import site.ymango.user.model.UserProfileUpdateModel

data class UserProfileUpdateRequest(
    @field:Length(min = 1, max = 8)
    var nickname: String?,

    var mbti: MBTI?,

    @field:Length(max = 4)
    var preferredMBTI: PreferredMBTI?,

    var sido: String?,
    var sigungu: String?,
    var location: Location?,

    @field:Min(130)
    @field:Max(210)
    var height: Int?,
    var bodyType: BodyType?,
    var job: String?,
    var smoking: Smoking?,
    var drinking: Drinking?,
    var religion: Religion?,

    @Length(min = 1, max = 100)
    var introduction: String?,

    @Length(min = 1, max = 200)
    var dream: String?,

    @Length(min = 1, max = 200)
    var loveStyle: String?,

    var charm: List<String>?,
    var interest: List<String>?,
) {
    fun toModel() = UserProfileUpdateModel(
        nickname = nickname,
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
