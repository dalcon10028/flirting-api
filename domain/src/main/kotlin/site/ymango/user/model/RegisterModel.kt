package site.ymango.user.model

import site.ymango.user.enums.*
import java.time.LocalDate

data class RegisterModel(
    val phoneNumber: String,
    val email: String,
    val deviceId: String,

    val nickname: String,
    val gender: Gender,
    val mbti: MBTI,
    val preferredMBTI: PreferredMBTI,
    val birthdate: LocalDate,
    val sido: String,
    val sigungu: String,
    val location: Location,
    val height: Int,
    val bodyType: BodyType,
    val job: String,
    val smoking: Smoking,
    val drinking: Drinking,
    val religion: Religion,
)