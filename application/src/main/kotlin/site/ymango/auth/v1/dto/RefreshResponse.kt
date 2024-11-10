package site.ymango.auth.v1.dto

data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long,
)
