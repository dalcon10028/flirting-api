package site.ymango.auth.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.auth.component.TokenProvider
import site.ymango.auth.model.JwtProperties
import site.ymango.user.UserService
import site.ymango.verification.VerificationService
import site.ymango.verification.enums.ReceptionType
import site.ymango.auth.v1.dto.*
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.user.model.*
import java.util.*

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val userService: UserService,
    private val tokenProvider: TokenProvider,
    private val jwtProperties: JwtProperties,
    private val verificationService: VerificationService,
) {
    companion object {
        private const val TEST_PHONE_NUMBER = "01000000000"
    }

    fun authenticate(authRequest: AuthenticationRequest, deviceId: String): AuthenticationResponse {
        val userModel: UserModel = when (authRequest.phoneNumber) {
            TEST_PHONE_NUMBER -> userService.findUser(authRequest.phoneNumber, deviceId)
            else -> {
                verificationService.verify(authRequest.phoneNumber, deviceId, ReceptionType.SMS, authRequest.code)
                val userModel = userService.findUser(authRequest.phoneNumber, deviceId)
                verificationService.isVerified(userModel.phoneNumber, userModel.deviceId, ReceptionType.SMS, 5)

                authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
                    userModel.userId.toString(),
                    userModel.deviceId,
                ))
                userModel
            }
        }

        val user: UserDetails = userDetailsService.loadUserByUsername(userModel.userId.toString())
        val accessToken = tokenProvider.generateToken(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
        )
        val refreshToken = tokenProvider.generateRefreshToken(userDetails = user)
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = jwtProperties.accessTokenExpiration,
            refreshExpiresIn = jwtProperties.refreshTokenExpiration,
        )
    }

    @Transactional
    fun register(registerRequest: RegisterRequest, deviceId: String): AuthenticationResponse {
        assert(registerRequest.height != null) { "height must not be null" }
        assert(registerRequest.longitude != null) { "longitude must not be null" }
        assert(registerRequest.latitude != null) { "latitude must not be null" }

        val user = UserModel(
            phoneNumber = registerRequest.phoneNumber,
            email = registerRequest.email,
            deviceId = deviceId,
        )
        val userProfile = UserProfileModel(
            nickname = registerRequest.nickname,
            gender = registerRequest.gender,
            birthdate = registerRequest.birthdate,
            mbti = registerRequest.mbti,
            preferredMBTI = registerRequest.preferredMBTI,
            sido = registerRequest.sido,
            sigungu = registerRequest.sigungu,
            location = Location(
                longitude = registerRequest.longitude!!,
                latitude = registerRequest.latitude!!,
            ),
            height = registerRequest.height!!,
            bodyType = registerRequest.bodyType,
            job = registerRequest.job,
            smoking = registerRequest.smoking,
            drinking = registerRequest.drinking,
            religion = registerRequest.religion,
        )


        val userId = userService.registerUser(user, userProfile, registerRequest.fcmToken).userId

        val userDetails: UserDetails = userDetailsService.loadUserByUsername(userId.toString())

        return AuthenticationResponse(
            accessToken = generateAccessToken(userDetails),
            refreshToken = tokenProvider.generateRefreshToken(userDetails),
            expiresIn = jwtProperties.accessTokenExpiration,
            refreshExpiresIn = jwtProperties.refreshTokenExpiration,
        )
    }

    fun refresh(token: String): RefreshResponse {
        if (tokenProvider.isRefreshTokenExpired(token)) {
            throw BaseException(ErrorCode.INVALID_REFRESH_TOKEN, "토큰이 만료되었습니다.")
        }
        val userId = tokenProvider.extractUserIdFromRefreshToken(token)

        return userService.findByUserId(userId).let {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(it.userId.toString())
            RefreshResponse(
                accessToken = generateAccessToken(userDetails),
                refreshToken = tokenProvider.generateRefreshToken(userDetails),
                expiresIn = jwtProperties.accessTokenExpiration,
                refreshExpiresIn = jwtProperties.refreshTokenExpiration,
            )
        }
    }

    private fun generateAccessToken(userDetails: UserDetails): String {
        return tokenProvider.generateToken(
            userDetails = userDetails,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
        )
    }
}