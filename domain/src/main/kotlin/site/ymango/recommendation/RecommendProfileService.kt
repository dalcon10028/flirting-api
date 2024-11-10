package site.ymango.recommendation

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.match.MatchRequestService
import site.ymango.point.PointService
import site.ymango.point.enums.PointType
import site.ymango.recommendation.entity.RecommendFilter
import site.ymango.recommendation.entity.RecommendProfile
import site.ymango.recommendation.enums.RecommendType
import site.ymango.recommendation.model.RecommendProfileModel
import site.ymango.recommendation.repository.RecommendFilterRepository
import site.ymango.recommendation.repository.RecommendProfileRepository
import site.ymango.user.UserAdditionalInformationService
import site.ymango.user.UserProfileService

@Service
class RecommendProfileService (
    private val recommendProfileRepository: RecommendProfileRepository,
    private val recommendFilterRepository: RecommendFilterRepository,
    private val userProfileService: UserProfileService,
    private val matchRequestService: MatchRequestService,
    private val pointService: PointService,
    private val userAdditionalInformationService: UserAdditionalInformationService,
) {

    @Transactional(readOnly = true)
    fun getRecommendProfiles(userProfileId: Long) =
        recommendProfileRepository.findByUserProfileId(userProfileId)
            .mapNotNull {
                RecommendProfileModel.ofNullable(it, userProfileService.getActiveOrRecommendInactiveProfileOrNull(it.recommendedId))
            }

    @Transactional
    fun getRecommendProfile(userProfileId: Long, recommendedProfileId: Long) : RecommendProfileModel {
        val recommendProfile = recommendProfileRepository.findByUserProfileIdAndRecommendProfileId(userProfileId, recommendedProfileId)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "해당 추천 프로필이 존재하지 않습니다.")

        recommendProfileRepository.save(recommendProfile.viewed())

        return RecommendProfileModel.of(recommendProfile, userProfileService.getActiveOrRecommendInactiveProfile(recommendProfile.recommendedId))
    }

    /**
     * 호감도 부여
     */
    @Transactional
    fun rateRecommendProfile(userProfileId: Long, recommendedProfileId: Long, rating: Int) {
        val recommendProfile = recommendProfileRepository.findByUserProfileIdAndRecommendProfileId(userProfileId, recommendedProfileId)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "해당 추천 프로필이 존재하지 않습니다.")

        if (recommendProfile.rating != null) throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "이미 호감도를 부여한 프로필입니다.")

        recommendProfileRepository.save(recommendProfile.rate(rating))
    }

    @Transactional
    fun flirtRecommendProfile(userId: Long, userProfileId: Long, recommendedProfileId: Long) {
        val recommendProfile = recommendProfileRepository.findByUserProfileIdAndRecommendProfileId(userProfileId, recommendedProfileId)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "해당 추천 프로필이 존재하지 않습니다.")

        pointService.applyPoint(userId, PointType.FLIRT)
        matchRequestService.createMatchRequest(userId, userProfileId, recommendProfile.recommendedId)
    }

    @Transactional
    fun expireRecommendProfile(userProfileId: Long, recommendedProfileId: Long) {
        val recommendProfile = recommendProfileRepository.findByUserProfileIdAndRecommendProfileId(userProfileId, recommendedProfileId)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "해당 추천 프로필이 존재하지 않습니다.")
        recommendProfileRepository.delete(recommendProfile)
    }

    @Transactional
    fun createRecommendProfile(userProfileId: Long, recommendedId: Long) : RecommendProfileModel {
        userProfileId.takeIf { it != recommendedId } ?: throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "자기 자신을 추천할 수 없습니다.")
        if (recommendProfileRepository.existsByUserProfileIdAndRecommendedId(userProfileId, recommendedId)) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "이미 추천한 프로필입니다.")
        }
        if (userProfileService.exists(userProfileId).not() || userProfileService.exists(recommendedId).not()) {
            throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "추천할 프로필이 존재하지 않습니다.")
        }

        return recommendProfileRepository.save(
            RecommendProfile(
                userProfileId = userProfileId,
                recommendedId = recommendedId,
                referenceType = RecommendType.ADMIN,
                referenceId = 0
            )
        ).let { RecommendProfileModel.from(it) }
    }

    @Transactional
    fun addPhoneNumberFilters(userProfileId: Long, phoneNumbers: List<String>) {
        recommendFilterRepository.deleteByUserProfileId(userProfileId)
        recommendFilterRepository.saveAll(
            phoneNumbers.map {
                RecommendFilter(userProfileId = userProfileId, phoneNumber = it)
        })
        userAdditionalInformationService.updatePhoneBlockUsing(userProfileId, true)
    }

    @Transactional
    fun deletePhoneNumberFilters(userProfileId: Long) {
        recommendFilterRepository.deleteByUserProfileId(userProfileId)
        userAdditionalInformationService.updatePhoneBlockUsing(userProfileId, false)
    }

    @Transactional
    fun createRecommendProfileUsePoint(userProfileId: Long) : RecommendProfileModel {
        pointService.applyPoint(userProfileId, PointType.CREATE_RECOMMEND_PROFILE)
        return createRecommendProfile(userProfileId, RecommendType.POINT) ?: throw BaseException(ErrorCode.NO_RECOMMENDABLE_PROFILE, "추천할 수 있는 이성이 없어요 잠시 후 다시 이용해주세요")
    }

    @Transactional
    fun createRecommendProfile(userProfileId: Long, recommendType: RecommendType, referenceId: Long? = null) : RecommendProfileModel? {
        val userProfile = userProfileService.getActiveProfile(userProfileId)

        return userProfileService.generateRecommendProfile(
            userProfileId = userProfileId,
            targetGender = userProfile.gender.opposite(),
            preferredMbtis = userProfile.preferredMBTI.getMBTIList(),
            location = userProfile.location
        )?.let {
            assert(userProfileId != it.userProfileId) { "자기 자신을 추천할 수 없습니다." }
            assert(it.userProfileId != null) { "추천할 프로필이 존재하지 않습니다." }

            RecommendProfileModel.from(recommendProfileRepository.save(
                RecommendProfile(
                    userProfileId = userProfileId,
                    recommendedId = it.userProfileId!!,
                    referenceType = recommendType,
                    referenceId = referenceId
                )
            ))
        }

    }
}