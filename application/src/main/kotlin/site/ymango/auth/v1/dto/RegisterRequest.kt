package site.ymango.auth.v1.dto

import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import jakarta.validation.constraints.*
import site.ymango.user.enums.*

data class RegisterRequest(
    @field:Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
    val phoneNumber: String,

    @field:Email
    val email: String,

    @field:NotEmpty
    @field:Length(min = 100, max = 200)
    val fcmToken: String,

    @field:NotEmpty
    @field:Length(min = 1, max = 8)
    val nickname: String,

    val gender: Gender,

    val mbti: MBTI,

    val preferredMBTI: PreferredMBTI,

    @field:Past
    val birthdate: LocalDate,

    @field:Length(max = 15)
    val sido: String,

    @field:Length(max = 15)
    val sigungu: String,

    @field:NotNull
    val longitude: Double?,

    @field:NotNull
    val latitude: Double?,

    @field:Max(300)
    @field:Min(130)
    @field:NotNull
    val height: Int?,

    val bodyType: BodyType,

    @field:Length(max = 20)
    val job: String,

    val smoking: Smoking,

    val drinking: Drinking,

    val religion: Religion,
)
