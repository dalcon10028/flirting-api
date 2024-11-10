package site.ymango.user.model

import site.ymango.user.entity.UserNotificationFilter
import site.ymango.user.enums.PushNotification

data class UserNotificationFilterModel(
    val userId: Long,
    val sendTemplate: PushNotification,
) {
    companion object {
        fun from(userNotificationFilter: UserNotificationFilter) = UserNotificationFilterModel(
            userId = userNotificationFilter.userId,
            sendTemplate = userNotificationFilter.sendTemplate,
        )
    }
}
