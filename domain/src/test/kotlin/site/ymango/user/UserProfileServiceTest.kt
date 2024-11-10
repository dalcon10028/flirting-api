package site.ymango.user

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.user.entity.UserProfile
import site.ymango.user.repository.UserProfileRepository
import java.time.LocalDate

import site.ymango.user.enums.*
import site.ymango.user.model.*

@SpringBootTest
class UserProfileServiceTest(
    private val userProfileService: UserProfileService,
    private val userProfileRepository: UserProfileRepository
) : FunSpec({

    context("UserProfileService") {
        userProfileRepository.deleteAll()

        test("닉네임이외에 다른 정보가 널이면 닉네임만 변경되고 다른 정보는 그대로여야 한다.") {
            val nickname = "nickname"
            val avatarCode = "MAN1"
            val companyName = "companyName"
            val mbti = MBTI.INFJ
            val preferredMBTI = PreferredMBTI.INFJ
            val sido = "서울"
            val sigungu = "강남구"
            val location = Location(0.0, 0.0)
            val gender = Gender.MALE
            val birthdate = LocalDate.now()
            val height = 180
            val bodyType = BodyType.CHUBBY
            val job = "개발자"
            val smoking = Smoking.NON_SMOKING
            val drinking = Drinking.NON_DRINKING
            val religion = Religion.BUDDHIST
            val introduction = "introduction"
            val dream = "dream"
            val loveStyle = "loveStyle"
            val charm = listOf("charm1", "charm2")
            val interest = listOf("interest1", "interest2")


            val save = userProfileRepository.saveAndFlush(
                UserProfile(
                    userId = 1L,
                    nickname = nickname,
                    avatarCode = avatarCode,
                    companyName = companyName,
                    gender = gender,
                    mbti = mbti,
                    preferredMBTI = preferredMBTI,
                    birthdate = birthdate,
                    sido = sido,
                    sigungu = sigungu,
                    location = location,
                    height = height,
                    bodyType = bodyType,
                    job = job,
                    smoking = smoking,
                    drinking = drinking,
                    religion = religion,
                    introduction = introduction,
                    dream = dream,
                    loveStyle = loveStyle,
                    charm = charm,
                    interest = interest
                )
            )

            val update = userProfileService.update(save.userProfileId!!, UserProfileUpdateModel(nickname = "newNickname"))

            update.nickname shouldBe "newNickname"
            update.companyName shouldBe companyName
            update.gender shouldBe gender
            update.mbti shouldBe mbti
            update.preferredMBTI shouldBe preferredMBTI
            update.birthdate shouldBe birthdate
            update.sido shouldBe sido
            update.sigungu shouldBe sigungu
            update.location shouldBe location
            update.height shouldBe height
            update.bodyType shouldBe bodyType
            update.job shouldBe job
            update.smoking shouldBe smoking
            update.drinking shouldBe drinking
            update.religion shouldBe religion
            update.introduction shouldBe introduction
            update.dream shouldBe dream
            update.loveStyle shouldBe loveStyle
            update.charm shouldBe charm
            update.interest shouldBe interest
        }
    }
})
