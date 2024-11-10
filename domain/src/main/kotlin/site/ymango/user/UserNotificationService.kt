package site.ymango.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.user.entity.UserNotificationFilter
import site.ymango.user.enums.PushNotification
import site.ymango.user.model.UserNotificationFilterModel
import site.ymango.user.repository.UserNotificationFilterRepository

@Service
class UserNotificationService(
    private val userNotificationFilterRepository: UserNotificationFilterRepository
) {

    @Transactional
    fun toggleFilter(userId: Long, sendTemplate: PushNotification) : List<UserNotificationFilterModel> {
        val filter = userNotificationFilterRepository.findByUserIdAndSendTemplate(userId, sendTemplate)
        if (filter != null) {
            userNotificationFilterRepository.delete(filter)
        } else {
            userNotificationFilterRepository.save(UserNotificationFilter(userId = userId, sendTemplate = sendTemplate))
        }

        return userNotificationFilterRepository.findByUserId(userId).map { UserNotificationFilterModel.from(it) }
    }
}