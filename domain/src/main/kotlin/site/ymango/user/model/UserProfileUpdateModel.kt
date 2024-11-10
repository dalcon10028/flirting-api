package site.ymango.user.model

import site.ymango.user.enums.*

data class UserProfileUpdateModel(
    var nickname: String? = null,
    var mbti: MBTI? = null,
    var preferredMBTI: PreferredMBTI? = null,
    var sido: String? = null,
    var sigungu: String? = null,
    var location: Location? = null,
    var height: Int? = null,
    var bodyType: BodyType? = null,
    var job: String? = null,
    var smoking: Smoking? = null,
    var drinking: Drinking? = null,
    var religion: Religion? = null,
    var introduction: String? = null,
    var dream: String? = null,
    var loveStyle: String? = null,
    var charm: List<String>? = null,
    var interest: List<String>? = null,
)
