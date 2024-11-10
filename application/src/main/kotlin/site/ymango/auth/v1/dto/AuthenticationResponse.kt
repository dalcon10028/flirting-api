package site.ymango.auth.v1.dto

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long,
)
