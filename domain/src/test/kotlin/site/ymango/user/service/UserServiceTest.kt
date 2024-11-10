package site.ymango.user.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.fail
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.company.CompanyService
import site.ymango.company.model.CompanyModel
import site.ymango.user.UserService
import java.time.LocalDate
import io.mockk.*
import site.ymango.user.enums.*
import site.ymango.user.model.*
import site.ymango.user.repository.UserProfileRepository
import site.ymango.user.repository.UserRepository
import site.ymango.verification.VerificationService

@SpringBootTest
class UserServiceTest(
    private val userService: UserService,
    private val userRepository: UserRepository,
    @MockkBean private val verificationService: VerificationService,
    private val userProfileRepository: UserProfileRepository,
): BehaviorSpec({
    given("UserService") {
        userRepository.deleteAll()
        userProfileRepository.deleteAll()
        val companyService = mockk<CompanyService>()

        every { companyService.createCompany(any()) } returns CompanyModel(domain = "abc.com", name = "테스트 회사이름")
        justRun { verificationService.isVerified(any(), any(), deviceId = any(), any()) }

        `when`("registerUser") {
            val userModel = UserModel(
                phoneNumber = "01012345678",
                email = "abc@abc.com",
                deviceId = "asdf",
            )

            val userProfileModel = UserProfileModel(
                nickname = "abc",
                gender = Gender.MALE,
                birthdate = LocalDate.of(1990, 1, 1),
                sido = "서울",
                sigungu = "강남구",
                mbti = MBTI.INFJ,
                preferredMBTI = PreferredMBTI.INFJ,
                location = Location(0.0, 0.0),
                height = 180,
                bodyType = BodyType.CHUBBY,
                job = "개발자",
                smoking = Smoking.NON_SMOKING,
                drinking = Drinking.NON_DRINKING,
                religion = Religion.BUDDHIST
            )

            userService.registerUser(userModel, userProfileModel, "fcmToken")

            val user = userRepository.findByPhoneNumber(userModel.phoneNumber) ?: fail("user is null")

            then("result") {
                user.email shouldBe userModel.email
                user.phoneNumber shouldBe userModel.phoneNumber
                user.deviceId shouldBe userModel.deviceId
            }
        }
    }
})
