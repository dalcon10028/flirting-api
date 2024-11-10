package site.ymango.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.ymango.user.entity.User

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByUserId(userId: Long): User?

    fun findByDeviceId(deviceId: String): User?

    fun findByPhoneNumber(phoneNumber: String): User?

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}