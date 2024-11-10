package site.ymango.admin.recommendation

import org.springframework.web.bind.annotation.*
import site.ymango.admin.recommendation.dto.RecommendProfileCreate
import site.ymango.admin.recommendation.dto.RecommendRandomProfileCreate
import site.ymango.recommendation.RecommendProfileService
import site.ymango.recommendation.enums.RecommendType

@RestController
@RequestMapping("/admin/recommended-profiles")
class AdminRecommendController(
    private val recommendProfileService: RecommendProfileService,
) {

    @PostMapping
    fun createRecommendProfile(
        @RequestBody request: RecommendProfileCreate
    ) {
        recommendProfileService.createRecommendProfile(request.userProfileId, request.referenceId)
    }

    @PostMapping("/random")
    fun createRandomProfile(
        @RequestBody request: RecommendRandomProfileCreate
    ) {
        recommendProfileService.createRecommendProfile(request.userProfileId, RecommendType.ADMIN)
    }

}