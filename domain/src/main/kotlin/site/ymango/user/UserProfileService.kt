package site.ymango.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.code.CodeService
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.user.enums.AvatarCode
import site.ymango.user.enums.Gender
import site.ymango.user.enums.MBTI
import site.ymango.user.enums.UserProfileStatus
import site.ymango.user.repository.UserProfileRepository

import site.ymango.user.model.*

@Service
class UserProfileService(
    private val userProfileRepository: UserProfileRepository,
    private val codeService: CodeService,
) {
    /**
     * 사용자 프로필 조회 (nullable)
     */
    @Transactional(readOnly = true)
    fun getActiveOrRecommendInactiveProfileOrNull(userProfileId: Long): UserProfileModel? =
        userProfileRepository.findByUserProfileIdAndStatusIn(userProfileId, listOf(UserProfileStatus.ACTIVE, UserProfileStatus.RECOMMEND_INACTIVE))?.let { UserProfileModel.from(it) }

    @Transactional(readOnly = true)
    fun getActiveOrRecommendInactiveProfile(userProfileId: Long): UserProfileModel =
        userProfileRepository.findByUserProfileIdAndStatusIn(userProfileId, listOf(UserProfileStatus.ACTIVE, UserProfileStatus.RECOMMEND_INACTIVE))
            ?.let { UserProfileModel.from(it) }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.")

    @Transactional(readOnly = true)
    fun getActiveProfile(userProfileId: Long): UserProfileModel {
        val userProfile = userProfileRepository.findByUserProfileIdAndStatusIn(userProfileId, listOf(UserProfileStatus.ACTIVE, UserProfileStatus.RECOMMEND_INACTIVE))
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.")

        if (userProfile.status == UserProfileStatus.RECOMMEND_INACTIVE) {
            throw BaseException(ErrorCode.RECOMMEND_INACTIVE_USER_PROFILE, "추천 비활성화된 프로필입니다 추천 프로필을 활성화 해주세요")
        }

        return UserProfileModel.from(userProfile)
    }

    @Transactional(readOnly = true)
    fun getUserProfileWithUser(userProfileId: Long): UserProfileModel =
        userProfileRepository.findByUserProfileId(userProfileId)?.let { UserProfileModel.from(it) }
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.")

    /**
     * 프로필 존재여부 추천 프로필 비활성화 미포함
     */
    @Transactional(readOnly = true)
    fun exists(profileId: Long): Boolean =
        userProfileRepository.existsByUserProfileIdAndStatus(profileId)

    /**
     * 프로필 추천 비활성화
     */
    @Transactional
    fun recommendDeactivate(userProfileId: Long) {
        val userProfile = userProfileRepository.findById(userProfileId).orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.") }
        userProfileRepository.save(userProfile.deactivate())
    }

    /**
     * 프로필 활성화
     */
    @Transactional
    fun activate(userProfileId: Long) {
        val userProfile = userProfileRepository.findById(userProfileId).orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.") }
        userProfileRepository.save(userProfile.activate())
    }


    /**
     * 사용자 프로필 수정
     * 포인트 사용하는 항목을 누적 포인트 사용
     */
    @Transactional
    fun update(userProfileId: Long, userProfileUpdate: UserProfileUpdateModel): UserProfileModel {
        val userProfile = userProfileRepository.findById(userProfileId).orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.") }

        // TODO: 포인트 사용 처리
        userProfile.apply {
            nickname = userProfileUpdate.nickname ?: nickname
            mbti = userProfileUpdate.mbti ?: mbti
            preferredMBTI = userProfileUpdate.preferredMBTI ?: preferredMBTI
            sido = userProfileUpdate.sido ?: sido
            sigungu = userProfileUpdate.sigungu ?: sigungu
            location = userProfileUpdate.location ?: location
            height = userProfileUpdate.height ?: height
            bodyType = userProfileUpdate.bodyType ?: bodyType
            job = userProfileUpdate.job ?: job
            smoking = userProfileUpdate.smoking ?: smoking
            drinking = userProfileUpdate.drinking ?: drinking
            religion = userProfileUpdate.religion ?: religion
            introduction = userProfileUpdate.introduction ?: introduction
            dream = userProfileUpdate.dream ?: dream
            loveStyle = userProfileUpdate.loveStyle ?: loveStyle
            charm = userProfileUpdate.charm ?: charm
            interest = userProfileUpdate.interest ?: interest
        }

        return userProfileRepository.save(userProfile).let { UserProfileModel.from(it) }
    }

    @Transactional
    fun block(userProfileId: Long) {
        val userProfile = userProfileRepository.findById(userProfileId).orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.") }
        userProfile.block()
        userProfileRepository.save(userProfile)
    }

    @Transactional
    fun unblock(userProfileId: Long) {
        val userProfile = userProfileRepository.findById(userProfileId).orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.") }
        userProfile.unblock()
        userProfileRepository.save(userProfile)
    }

    @Transactional
    fun regenerateAvatar(userProfileId: Long): UserProfileModel {
        val userProfile = userProfileRepository.findById(userProfileId).orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "사용자 프로필을 찾을 수 없습니다.") }
        generateProfileAvatarCode(userProfile.gender).let {
            userProfile.avatarCode = it
        }
        return userProfileRepository.save(userProfile).let { UserProfileModel.from(it) }
    }

    fun generateProfileAvatarCode(gender: Gender): String {
        when (gender) {
            Gender.MALE -> codeService.getCodes(AvatarCode.MALE_AVATAR_CODE.name)
            Gender.FEMALE -> codeService.getCodes(AvatarCode.FEMALE_AVATAR_CODE.name)
        }.random().let {
            return it.code
        }
    }

    fun generateRecommendProfile(userProfileId: Long, targetGender: Gender, preferredMbtis: List<MBTI>, location: Location): UserProfileModel? =
        userProfileRepository.generateRecommendProfile(
            userProfileId = userProfileId,
            targetGender = targetGender,
            preferredMbtis = preferredMbtis.map { it.name },
//            location = location
        )?.let { UserProfileModel.from(it) }
}