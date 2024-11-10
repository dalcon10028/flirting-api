package site.ymango.auth.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import site.ymango.user.enums.UserStatus

data class UserInfo(
    val userId: Long,
    val userProfileId: Long,
    val phoneNumber: String,
    val deviceId: String,
    private val email: String,
    private val password: String,
    private val userStatus: UserStatus,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = userId.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = userStatus != UserStatus.BLOCK

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}