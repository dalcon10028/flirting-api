package site.ymango.user.model

import site.ymango.user.entity.User
import site.ymango.user.entity.UserAdditionalInformation
import site.ymango.user.entity.UserProfile
import site.ymango.user.enums.UserRole
import site.ymango.user.enums.UserStatus
import java.time.LocalDateTime

data class UserModel(
    val userId: Long? = null,
    var phoneNumber: String,
    var email: String,
    var deviceId: String,
    var status: UserStatus = UserStatus.ACTIVE,
    val role: UserRole = UserRole.USER,
    var updatedAt: LocalDateTime? = null,
    var createdAt: LocalDateTime? = null,
    var userProfile: UserProfileModel? = null,
    val userAdditionalInformation: UserAdditionalInformationModel? = null,
    val userNotificationFilters: List<UserNotificationFilterModel> = emptyList(),
) {
    fun toEntity() = User(
        userId = userId,
        phoneNumber = phoneNumber,
        email = email,
        deviceId = deviceId,
        status = status,
        role = role,
        userNotificationFilters = emptyList()
    )

    companion object {
        fun from (user: User) = UserModel(
            userId = user.userId,
            phoneNumber = user.phoneNumber,
            email = user.email,
            deviceId = user.deviceId,
            status = user.status,
            role = user.role,
            updatedAt = user.updatedAt,
            createdAt = user.createdAt,
        )

        fun of(user: User, userProfile: UserProfile, userAdditionalInformation: UserAdditionalInformation) = UserModel(
            userId = user.userId,
            phoneNumber = user.phoneNumber,
            email = user.email,
            deviceId = user.deviceId,
            status = user.status,
            role = user.role,
            updatedAt = user.updatedAt,
            createdAt = user.createdAt,
            userProfile = UserProfileModel.from(userProfile),
            userAdditionalInformation = UserAdditionalInformationModel.from(userAdditionalInformation),
            userNotificationFilters = user.userNotificationFilters.map { UserNotificationFilterModel.from(it) }
        )

        fun ofNullable(user: User?) = user?.let { UserModel(
            userId = user.userId,
            phoneNumber = user.phoneNumber,
            email = user.email,
            deviceId = user.deviceId,
            status = user.status,
            role = user.role,
            updatedAt = user.updatedAt,
            createdAt = user.createdAt,
            userProfile = UserProfileModel.from(user.userProfile!!),
            userAdditionalInformation = UserAdditionalInformationModel.from(user.userAdditionalInformation!!),
            userNotificationFilters = user.userNotificationFilters.map { UserNotificationFilterModel.from(it) }
        )}
    }
}
