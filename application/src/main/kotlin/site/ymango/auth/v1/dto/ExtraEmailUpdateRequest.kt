package site.ymango.auth.v1.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ExtraEmailUpdateRequest(
    @field:NotBlank
    @field:Email
    val extraEmail: String,

    @field:NotBlank
    @field:Size(min = 6, max = 6)
    val code: String,
)
