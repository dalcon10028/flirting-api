package site.ymango.user.v1

import org.springframework.web.bind.annotation.*
import site.ymango.auth.model.UserInfo
import site.ymango.common.annotation.Auth
import site.ymango.user.UserProfileService
import site.ymango.user.v1.dto.UserProfileResponse
import site.ymango.user.v1.dto.UserProfileUpdateRequest

@RestController
@RequestMapping("/v1/user-profiles")
class UserProfileController(
    private val userProfileService: UserProfileService
) {
    @PatchMapping
    fun update(
        @Auth user: UserInfo,
        @RequestBody request: UserProfileUpdateRequest,
    ) = userProfileService.update(user.userProfileId, request.toModel()).let {
        UserProfileResponse.from(it)
    }

    @PostMapping("/deactivate")
    fun deactivate(@Auth user: UserInfo) {
        userProfileService.recommendDeactivate(user.userProfileId)
    }

    @PostMapping("/activate")
    fun activate(@Auth user: UserInfo) {
        userProfileService.activate(user.userProfileId)
    }

}