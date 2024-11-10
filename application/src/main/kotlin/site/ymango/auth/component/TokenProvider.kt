package site.ymango.auth.component

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import site.ymango.auth.model.JwtProperties
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

private val logger = KotlinLogging.logger {}
@Component
class TokenProvider(
    private val jwtProperties: JwtProperties,
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
    private val refreshKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.refreshSecret.toByteArray())

    fun generateToken(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .subject(userDetails.username)
            .claims(additionalClaims)
            .signWith(key)
            .issuedAt(Date.from(Instant.now()))
            .expiration(expirationDate)
            .compact()

    fun generateRefreshToken(userDetails: UserDetails): String =
        Jwts.builder()
            .subject(userDetails.username)
            .signWith(refreshKey)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plusSeconds(jwtProperties.refreshTokenExpiration)))
            .compact()

    fun extractUserIdFromRefreshToken(token: String): Long = try {
        Jwts.parser()
            .verifyWith(refreshKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject.toLong()
    } catch (e: Exception) {
        logger.error { "Failed to parse token: $token" }
        throw BaseException(ErrorCode.INVALID_TOKEN, "Invalid token")
    }

    fun isRefreshTokenExpired(token: String): Boolean = try {
        Jwts.parser()
            .verifyWith(refreshKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .expiration.before(Date.from(Instant.now()))
    } catch (e: Exception) {
        logger.error { "Failed to parse token: $token" }
        throw BaseException(ErrorCode.INVALID_TOKEN, "Invalid token")
    }

    fun extractUserId(token: String): String = getAllClaims(token).subject

    fun isTokenExpired(token: String): Boolean = getAllClaims(token).expiration.before(Date.from(Instant.now()))

    fun isValidToken(token: String, userDetails: UserDetails): Boolean =
        extractUserId(token) == userDetails.username && !isTokenExpired(token)

    fun getAllClaims(token: String): Claims = try {
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    } catch (e: Exception) {
        logger.error { "Failed to parse token: $token" }
        throw BaseException(ErrorCode.INVALID_TOKEN, "Invalid token")
    }
}