package site.ymango.company.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.*
import site.ymango.company.CompanyService
import site.ymango.company.entity.Company
import site.ymango.company.model.CompanyModel
import site.ymango.company.repository.CompanyRepository
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode

@SpringBootTest
internal class CompanyServiceTest(
    private val companyService: CompanyService,
    private val companyRepository: CompanyRepository
) : BehaviorSpec({
    beforeContainer {
        companyRepository.deleteAll()
    }
    given("companyService.createCompany") {
        `when`("정상적인 입력으로 회사를 생성하면") {
            val company = CompanyModel(
                name = "업라이즈",
                domain = "heybit.io",
                iconUrl = "123",
            )
            val createdCompany = companyService.createCompany(company)
            then("회사가 생성된다.") {
                createdCompany.name shouldBe "업라이즈"
                createdCompany.domain shouldBe "heybit.io"
                createdCompany.iconUrl shouldBe "123"
            }
        }

        `when`("중복 도메인으로 회사를 생성하면") {
            val company = CompanyModel(
                name = "업라이즈",
                domain = "heybit.io",
                iconUrl = "123",
            )
            companyService.createCompany(company)
            then("에러가 발생한다.") {
                val shouldThrow = shouldThrow<BaseException> {
                    companyService.createCompany(company)
                }
                shouldThrow.code shouldBe ErrorCode.DUPLICATE_RESOURCE
                shouldThrow.message shouldBe "이미 존재하는 도메인입니다."
            }
        }

        `when`("유효하지 않은 도메인으로 회사를 생성하면") {
            val company = CompanyModel(
                name = "업라이즈",
                domain = "heybit",
                iconUrl = "123",
            )
            then("에러가 발생한다.") {
                val shouldThrow = shouldThrow<BaseException> {
                    companyService.createCompany(company)
                }
                shouldThrow.code shouldBe ErrorCode.INVALID_INPUT_VALUE
                shouldThrow.message shouldBe "유효한 도메인을 입력해주세요"
            }
        }
    }

    given("companyService.getCompanyName") {
        `when`("존재하는 도메인을 입력하면") {
            companyRepository.save(Company(name = "업라이즈", domain = "heybit.io", iconUrl = "123"))
            then("회사 이름이 나온다.") {
                val companyName = companyService.getCompanyName("heybit.io")
                companyName shouldBe "업라이즈"
            }
        }

        `when`("존재하지 않는 도메인을 입력하면") {
            then("새회사가 나온다.") {
                val companyName = companyService.getCompanyName("heybit.io")
                companyName shouldBe "새회사"
            }
        }
    }

    given("테스트 회사가 11개 등록되어 있을 때") {
        companyRepository.saveAll(
            (1..11).map { Company(name = "업라이즈$it", domain = "heybit$it.io", iconUrl = "123") }
        )
        `when`("업라이즈로 10페이지 사이즈로 검색하면") {
            val cursor = 0
            val pageable = PageRequest.of(cursor, 10, Sort.by("companyId").ascending())
            val companies = companyService.getCompanies("업라이즈", cursor, pageable)
            then("업라이즈로 시작하는 회사가 10개가 나온다.") {
                companies.size shouldBe 10
            }
        }

        `when`("업라이즈로 5페이지 사이즈로 검색하면") {
            val cursor = 0
            val pageable = PageRequest.of(cursor, 5, Sort.by("companyId").ascending())
            val companies = companyService.getCompanies("업라이즈", cursor, pageable)
            then("업라이즈로 시작하는 회사가 5개가 나온다.") {
                companies.size shouldBe 5
            }
        }

        `when`("빈 문자열로 입력하면") {
            val cursor = 0
            val pageable = PageRequest.of(cursor, 10, Sort.by("companyId").ascending())
            val companies = companyService.getCompanies("", cursor, pageable)
            then("업라이즈로 시작하는 회사가 10개가 나온다.") {
                companies.size shouldBe 10
            }
        }
    }

    given("업라이즈라는 회사가 등록되어있을 때") {
        `when`("'업'이라는 이름으로 검색하면") {
            companyRepository.save(Company(name = "업라이즈", domain = "abc.com", iconUrl = "123"))
            val companyName = companyService.getCompanies("업", 0, PageRequest.of(0, 10, Sort.by("companyId").ascending())).first().name
            then("업라이즈라는 회사 이름이 나온다.") {
                companyName shouldBe "업라이즈"
            }
        }
    }
})
