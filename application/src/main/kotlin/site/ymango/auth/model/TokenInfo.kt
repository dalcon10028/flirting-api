package site.ymango.auth.model

data class TokenInfo(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long,
)
