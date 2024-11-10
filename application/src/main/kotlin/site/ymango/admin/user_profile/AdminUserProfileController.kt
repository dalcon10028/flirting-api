package site.ymango.admin.user_profile

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.ymango.admin.user_profile.dto.ProfileAvatarUpdate
import site.ymango.user.UserProfileService

@RestController
@RequestMapping("/admin/user-profiles")
class AdminUserProfileController(
    private val userProfileService: UserProfileService,
) {

    @PostMapping("/{userProfileId}/block")
    fun block(
        @PathVariable userProfileId: Long
    ) = userProfileService.block(userProfileId)


    @PostMapping("/{userProfileId}/unblock")
    fun unblock(
        @PathVariable userProfileId: Long
    ) = userProfileService.unblock(userProfileId)

    @PutMapping("/{userProfileId}/avatar/regenerate")
    fun regenerateAvatar(
        @PathVariable userProfileId: Long,
    ) = userProfileService.regenerateAvatar(userProfileId)
}