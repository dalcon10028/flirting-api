package site.ymango.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.user.entity.UserNotificationFilter
import site.ymango.user.enums.PushNotification

interface UserNotificationFilterRepository : JpaRepository<UserNotificationFilter, Long> {
    fun findByUserIdAndSendTemplate(userId: Long, sendTemplate: PushNotification): UserNotificationFilter?
    fun findByUserId(userId: Long): List<UserNotificationFilter>
}