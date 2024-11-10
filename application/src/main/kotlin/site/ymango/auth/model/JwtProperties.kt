package site.ymango.auth.model

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val secret: String,
    val refreshSecret: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long,
)
