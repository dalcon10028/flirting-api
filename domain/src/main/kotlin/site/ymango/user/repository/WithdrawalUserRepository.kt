package site.ymango.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.ymango.user.entity.WithdrawalUser

@Repository
interface WithdrawalUserRepository : JpaRepository<WithdrawalUser, Long> {
    fun existsByPhoneNumberOrDeviceId(phoneNumber: String, deviceId: String): Boolean
}