package site.ymango.auth.service

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import site.ymango.auth.model.UserInfo
import site.ymango.user.UserAdditionalInformationService
import site.ymango.user.UserService

@Service
class CustomUserDetailsService(
    private val userService: UserService,
    private val userAdditionalInformationService: UserAdditionalInformationService,
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserInfo {
        userAdditionalInformationService.updateLastAccessedAt(userId.toLong())

        userService.findByUserId(userId.toLong()).let {
            assert(it.userId != null)
            assert(it.userProfile?.userProfileId != null)

            return UserInfo(
                userId = it.userId!!,
                userProfileId = it.userProfile?.userProfileId!!,
                email = it.email,
                phoneNumber = it.phoneNumber,
                deviceId = it.deviceId,
                password = "{noop}${it.deviceId}",
                userStatus = it.status,
            )
        }
    }
}