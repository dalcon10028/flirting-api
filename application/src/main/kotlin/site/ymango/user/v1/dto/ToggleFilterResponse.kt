package site.ymango.user.v1.dto

import site.ymango.user.enums.PushNotification
import site.ymango.user.model.UserNotificationFilterModel

data class ToggleFilterResponse(
    val sendPushNotification: Map<PushNotification, Boolean>
) {

    companion object {
        fun from(userNotificationFilters: List<UserNotificationFilterModel>): ToggleFilterResponse {
            return PushNotification.entries.associateWith {
                userNotificationFilters.any { filter -> filter.sendTemplate == it }
            }.let {
                ToggleFilterResponse(it)
            }
        }
    }
}
