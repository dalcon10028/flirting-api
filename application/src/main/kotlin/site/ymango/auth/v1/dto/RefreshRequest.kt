package site.ymango.auth.v1.dto

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @NotBlank
    val refreshToken: String,
)
