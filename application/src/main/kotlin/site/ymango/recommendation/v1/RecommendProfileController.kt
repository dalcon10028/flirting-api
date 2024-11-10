package site.ymango.recommendation.v1

import org.springframework.web.bind.annotation.*
import site.ymango.auth.model.UserInfo
import site.ymango.common.annotation.Auth
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.recommendation.RecommendProfileService
import site.ymango.recommendation.enums.RecommendType
import site.ymango.recommendation.v1.dto.PhoneNumberFilterRequest
import site.ymango.recommendation.v1.dto.RecommendProfileResponse

@RestController
@RequestMapping("/v1/recommended-profiles")
class RecommendProfileController(
    private val recommendProfileService: RecommendProfileService,
) {
    @GetMapping
    fun getRecommendedProfiles(
        @Auth user: UserInfo,
    ) : List<RecommendProfileResponse> =
        recommendProfileService.getRecommendProfiles(user.userProfileId)
            .map { RecommendProfileResponse.from(it) }

    @GetMapping("/{recommendedProfileId}")
    fun getRecommendedProfile(
        @Auth user: UserInfo,
        @PathVariable recommendedProfileId: Long,
    ) : RecommendProfileResponse =
        RecommendProfileResponse.from(recommendProfileService.getRecommendProfile(user.userProfileId, recommendedProfileId))

    @PostMapping("/{recommendedProfileId}/rate")
    fun rateRecommendedProfile(
        @Auth user: UserInfo,
        @PathVariable recommendedProfileId: Long,
        @RequestParam rating: Int,
    ) {
        recommendProfileService.rateRecommendProfile(user.userProfileId, recommendedProfileId, rating)
    }

    @PostMapping("/{recommendedProfileId}/flirt")
    fun flirtRecommendedProfile(
        @Auth user: UserInfo,
        @PathVariable recommendedProfileId: Long,
    ) {
        recommendProfileService.flirtRecommendProfile(user.userId, user.userProfileId, recommendedProfileId)
    }

    @DeleteMapping("/{recommendedProfileId}")
    fun deleteRecommendedProfile(
        @Auth user: UserInfo,
        @PathVariable recommendedProfileId: Long,
    ) {
        recommendProfileService.expireRecommendProfile(user.userProfileId, recommendedProfileId)
    }

    @PostMapping("/add-phone-number-filters")
    fun addPhoneNumberFilters(
        @Auth user: UserInfo,
        @RequestBody body: PhoneNumberFilterRequest,
    ) {
        recommendProfileService.addPhoneNumberFilters(user.userProfileId, body.phoneNumbers)
    }

    @DeleteMapping("/delete-phone-number-filters")
    fun deletePhoneNumberFilters(
        @Auth user: UserInfo,
    ) {
        recommendProfileService.deletePhoneNumberFilters(user.userProfileId)
    }

    @PostMapping("/create")
    fun createRecommendedProfile(
        @Auth user: UserInfo,
    ) {
        recommendProfileService.createRecommendProfileUsePoint(user.userId)
    }
}