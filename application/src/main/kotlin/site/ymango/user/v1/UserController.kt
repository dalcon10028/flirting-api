package site.ymango.user.v1

import jakarta.validation.Valid
import site.ymango.auth.model.UserInfo
import site.ymango.common.annotation.Auth
import site.ymango.user.UserService
import site.ymango.user.v1.dto.MeResponse

import org.springframework.web.bind.annotation.*
import site.ymango.auth.v1.dto.ExtraEmailUpdateRequest
import site.ymango.user.enums.PushNotification
import site.ymango.user.*
import site.ymango.user.v1.dto.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService,
    private val userNotificationService: UserNotificationService,
    private val userAdditionalInformationService: UserAdditionalInformationService,
) {

    @GetMapping("/me")
    fun getMe(
        @Auth user: UserInfo
    ): MeResponse = MeResponse.from(userService.findByUserId(user.userId))

    @DeleteMapping
    fun withdrawal(
        @Auth user: UserInfo
    ) {
        userService.withdrawalUser(user.userId)
    }

    @PostMapping("/marketing/agree")
    fun agreeMarketing(
        @Auth user: UserInfo
    ): AgreeMarketingResponse {
        return AgreeMarketingResponse(userAdditionalInformationService.agreeMarketing(user.userId))
    }

    @PostMapping("/marketing/disagree")
    fun disagreeMarketing(
        @Auth user: UserInfo
    ) {
        userAdditionalInformationService.disagreeMarketing(user.userId)
    }

    @PutMapping("/fcm-token")
    fun updateFcmToken(
        @Auth user: UserInfo,
        @Valid @RequestBody body: FcmTokenUpdate
    ) {
        userAdditionalInformationService.updateFcmToken(user.userId, body.fcmToken)
    }

    @PutMapping("/extra-email")
    fun updateExtraEmail(
        @Auth user: UserInfo,
        @Valid @RequestBody extraEmailUpdate: ExtraEmailUpdateRequest,
    ) {
        userAdditionalInformationService.updateExtraEmail(user.userId, user.deviceId, extraEmailUpdate.extraEmail, extraEmailUpdate.code)
    }

    @PostMapping("/toggle-notification-filter/{sendTemplate}")
    fun toggleNotificationFilter(
        @Auth user: UserInfo,
        @PathVariable sendTemplate: PushNotification
    ) : ToggleFilterResponse =
        userNotificationService.toggleFilter(user.userId, sendTemplate).let { ToggleFilterResponse.from(it) }
}