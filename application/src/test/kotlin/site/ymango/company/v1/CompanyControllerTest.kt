package site.ymango.company.v1

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.mockk.every
import jakarta.validation.ConstraintViolationException
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import site.ymango.auth.component.TokenProvider
import site.ymango.auth.model.JwtProperties
import site.ymango.company.CompanyService
import site.ymango.company.model.CompanyModel
import site.ymango.company.v1.dto.CompanyRequest

@WebMvcTest(value = [CompanyController::class, TokenProvider::class, JwtProperties::class])
internal class CompanyControllerTest(
    private val companyController: CompanyController,
    @MockkBean private val companyService: CompanyService
) : FunSpec({
    every { companyService.getCompanies(any(), any(), any()) } returns SliceImpl(emptyList())
    every { companyService.createCompany(any()) } returns CompanyModel(1, "name", "domain")

    test("getCompanies should return a list from companies").config(enabled = false) {
        val companies = companyController.getCompanies("name", 1, Pageable.unpaged())

        companies shouldBe SliceImpl(emptyList())
    }

    test("getCompanies should throw exception when name is too long").config(enabled = false) {
        val shouldThrow = shouldThrow<ConstraintViolationException> {
            companyController.getCompanies("a".repeat(31), 1, Pageable.unpaged())
        }

        shouldThrow.message shouldBe "getCompanies.name: 길이가 0에서 30 사이여야 합니다"
    }

    test("getCompanies should throw exception when cursor is negative").config(enabled = false) {
        val shouldThrow = shouldThrow<ConstraintViolationException> {
            companyController.getCompanies("name", -1, Pageable.unpaged())
        }

        shouldThrow.message shouldBe "getCompanies.cursor: 0 이상이어야 합니다"
    }

    test("createCompany should throw exception when name is too long").config(enabled = false) {
        val shouldThrow = shouldThrow<ConstraintViolationException> {
            companyController.createCompany(CompanyRequest("a".repeat(41), "domain"))
        }

        shouldThrow.message shouldContain "createCompany.company.name: 길이가 40이하여야 합니다"
    }

    test("createCompany should throw exception when name is empty").config(enabled = false) {
        val shouldThrow = shouldThrow<ConstraintViolationException> {
            companyController.createCompany(CompanyRequest("", "domain"))
        }

        shouldThrow.message shouldContain "createCompany.company.name: 비어 있을 수 없습니다"
    }

    test("createCompany should throw exception when domain is invalid").config(enabled = false) {
        val shouldThrow = shouldThrow<ConstraintViolationException> {
            companyController.createCompany(CompanyRequest("name", "domain!"))
        }

        shouldThrow.message shouldBe "createCompany.company.domain: 유효한 도메인을 입력해주세요"
    }
})
