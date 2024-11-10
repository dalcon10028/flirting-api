package site.ymango.recommendation

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.justRun
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.match.MatchRequestService
import site.ymango.point.PointService
import site.ymango.recommendation.entity.RecommendProfile
import site.ymango.recommendation.enums.RecommendType
import site.ymango.recommendation.model.RecommendProfileModel
import site.ymango.recommendation.repository.RecommendProfileRepository
import site.ymango.send.SendEventListener
import site.ymango.user.UserProfileService
import site.ymango.user.enums.*
import site.ymango.user.model.Location
import site.ymango.user.model.UserProfileModel
import java.time.LocalDate
import java.time.LocalDateTime


@SpringBootTest
class RecommendProfileServiceTest(
    private val recommendProfileService: RecommendProfileService,
    private val recommendProfileRepository: RecommendProfileRepository,
    @MockkBean private val userProfileService: UserProfileService,
    @MockkBean private val matchRequestService: MatchRequestService,
    @MockkBean private val pointService: PointService,
    @MockkBean private val eventListener: SendEventListener,
) : BehaviorSpec({

    beforeContainer {
        recommendProfileRepository.deleteAll()

        every { userProfileService.getActiveOrRecommendInactiveProfileOrNull(2) } returns null
        every { userProfileService.getActiveOrRecommendInactiveProfileOrNull(3) } returns UserProfileModel(
            userProfileId = 3,
            userId = 3,
            nickname = "test",
            avatarCode = "test",
            companyName = "test",
            gender = Gender.MALE,
            birthdate = LocalDate.now().minusYears(20),
            mbti = MBTI.INFJ,
            preferredMBTI = PreferredMBTI.INFJ,
            sido = "test",
            sigungu = "test",
            location = Location(1.0, 1.0),
            height = 180,
            bodyType = BodyType.CHUBBY,
            job = "test",
            smoking = Smoking.NON_SMOKING,
            drinking = Drinking.NON_DRINKING,
            religion = Religion.NONE,
            introduction = "test",
            dream = "test",
            loveStyle = "test",
            charm = listOf("test"),
            interest = listOf("test"),
        )
        every { userProfileService.getActiveOrRecommendInactiveProfileOrNull(4) } returns UserProfileModel(
            userProfileId = 4,
            userId = 4,
            nickname = "test",
            avatarCode = "test",
            companyName = "test",
            gender = Gender.MALE,
            birthdate = LocalDate.now().minusYears(20),
            mbti = MBTI.INFJ,
            preferredMBTI = PreferredMBTI.INFJ,
            sido = "test",
            sigungu = "test",
            location = Location(1.0, 1.0),
            height = 180,
            bodyType = BodyType.CHUBBY,
            job = "test",
            smoking = Smoking.NON_SMOKING,
            drinking = Drinking.NON_DRINKING,
            religion = Religion.NONE,
            introduction = "test",
            dream = "test",
            loveStyle = "test",
            charm = listOf("test"),
            interest = listOf("test"),
        )
        justRun { eventListener.send(any()) }
    }

    given("추천 프로필 목록을 조회할 때") {
        `when`("탈퇴한 유저가 있으면") {
            then("탈퇴한 유저를 제외한 프로필 목록을 조회한다.") {
                recommendProfileRepository.saveAll(listOf(
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 2,
                        referenceType = RecommendType.TEST,
                        referenceId = 1,
                    ),
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 3,
                        referenceType = RecommendType.TEST,
                        referenceId = 1,
                    ),
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 4,
                        referenceType = RecommendType.TEST,
                        referenceId = 1,
                    )
                ))
                val recommendProfiles: List<RecommendProfileModel> = recommendProfileService.getRecommendProfiles(1)
                recommendProfiles.size shouldBe 2
            }
        }

        `when`("추천 만료된 유저가 있으면") {
            then("추천 만료된 유저를 제외한 프로필 목록을 조회한다.") {
                recommendProfileRepository.saveAll(listOf(
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 2,
                        referenceType = RecommendType.TEST,
                        referenceId = 1,
                    ),
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 3,
                        referenceType = RecommendType.TEST,
                        referenceId = 1,
                        expiredAt = LocalDateTime.now().minusDays(1),
                    ),
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 4,
                        referenceType = RecommendType.TEST,
                        referenceId = 1,
                    )
                ))
                val recommendProfiles: List<RecommendProfileModel> = recommendProfileService.getRecommendProfiles(1)
                recommendProfiles.size shouldBe 1
            }
        }
    }

    given("호감도를 부여할 때") {
        `when`("6점을 부여하면") {
            then("예외가 발생한다.") {
                val save = recommendProfileRepository.save(
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 2,
                        referenceType = RecommendType.TEST,
                        referenceId = 2,
                    )
                )
                shouldThrow<BaseException> {
                    recommendProfileService.rateRecommendProfile(1, save.recommendProfileId!!, 6)
                }.message shouldBe "rating은 1~5 사이의 값이어야 합니다."
            }
        }

        `when`("-1점을 부여하면") {
            then("예외가 발생한다.") {
                val save = recommendProfileRepository.save(
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 2,
                        referenceType = RecommendType.TEST,
                        referenceId = 2,
                    )
                )
                shouldThrow<BaseException> {
                    recommendProfileService.rateRecommendProfile(1, save.recommendProfileId!!, 6)
                }.message shouldBe "rating은 1~5 사이의 값이어야 합니다."
            }
        }


        `when`("이미 호감도를 부여한 추천 프로필이라면") {
            then("예외가 발생한다.") {
                val save = recommendProfileRepository.save(
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 2,
                        referenceType = RecommendType.TEST,
                        referenceId = 2,
                    )
                )
                recommendProfileService.rateRecommendProfile(1, save.recommendProfileId!!, 3)

                shouldThrow<BaseException> {
                    recommendProfileService.rateRecommendProfile(1, save.recommendProfileId!!, 3)
                }.message shouldBe "이미 호감도를 부여한 프로필입니다."
            }
        }
    }

    given("플러팅 요청을 보낼 때") {
        `when`("추천 프로필이 존재하지 않으면") {
            then("예외가 발생한다.") {
                shouldThrow<BaseException> {
                    recommendProfileService.flirtRecommendProfile(1, 2)
                }.message shouldBe "해당 추천 프로필이 존재하지 않습니다."
            }
        }

        `when`("성공하면") {
            justRun { pointService.applyPoint(any(), any(), any()) }
            justRun { matchRequestService.createMatchRequest(1, 3) }
            then("플러팅 요청은 만료된다.") {

                val save = recommendProfileRepository.save(
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 3,
                        referenceType = RecommendType.TEST,
                        referenceId = 2,
                    )
                )
                recommendProfileService.flirtRecommendProfile(1, save.recommendProfileId!!)
                recommendProfileRepository.findByUserProfileIdAndRecommendProfileId(1, save.recommendProfileId!!) shouldBe null
            }
        }

        `when`("플러팅 요청이 실패하면") {
            justRun { pointService.applyPoint(any(), any(), any()) }
            every { matchRequestService.createMatchRequest(1, 3) } throws BaseException(ErrorCode.INVALID_INPUT_VALUE, "이미 플러팅 요청을 보낸 상대입니다.")

            then("예외가 발생한다.") {

                val save = recommendProfileRepository.save(
                    RecommendProfile(
                        userProfileId = 1,
                        recommendedId = 3,
                        referenceType = RecommendType.TEST,
                        referenceId = 2,
                    )
                )
                shouldThrow<BaseException> {
                    recommendProfileService.flirtRecommendProfile(1, save.recommendProfileId!!)
                }.message shouldBe "이미 플러팅 요청을 보낸 상대입니다."
            }
        }
    }

    given("추천 프로필을 만들 때") {
        `when`( "추천할 수 있는 사람이 없으면") {
            then("아무일도 일어나지 않는다.") {

            }
        }
    }
})
