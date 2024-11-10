package site.ymango.auth.v1.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class AuthenticationRequest(
    @field:NotBlank(message = "전화번호를 입력해주세요.")
    @field:Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    val phoneNumber: String,

    @field:NotBlank(message = "인증번호를 입력해주세요.")
    @field:Size(min = 6, max = 6, message = "인증번호가 올바르지 않습니다.")
    val code: String,
)
