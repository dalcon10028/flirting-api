package site.ymango.company

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.company.model.*
import site.ymango.company.repository.CompanyRepository
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.validation.DOMAIN_REGEX

@Service
class CompanyService(
    private val companyRepository: CompanyRepository
) {
    @Transactional(readOnly = true)
    fun getCompanies(nameKeyword: String, cursor: Int, pageable: Pageable): Slice<CompanyModel> = companyRepository.findByName(nameKeyword, cursor, pageable).map { it.toModel() }

    @Transactional(readOnly = true)
    fun getCompanyName(domain: String): String = companyRepository.findByDomain(domain)?.name ?: "새회사"


    @Transactional
    fun createCompany(company: CompanyModel): CompanyModel {
        if (DOMAIN_REGEX.toRegex().matches(company.domain).not()) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "유효한 도메인을 입력해주세요")
        }

        if (companyRepository.existsByDomain(company.domain)) {
            throw BaseException(ErrorCode.DUPLICATE_RESOURCE, "이미 존재하는 도메인입니다.")
        }
        return companyRepository.save(company.toEntity()).toModel()
    }
}