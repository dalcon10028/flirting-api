package site.ymango.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.user.entity.UserAdditionalInformation

interface UserAdditionalInformationRepository : JpaRepository<UserAdditionalInformation, Long> {
    fun findByUserId(userId: Long): UserAdditionalInformation?
}