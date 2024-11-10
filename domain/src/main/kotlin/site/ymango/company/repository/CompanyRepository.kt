package site.ymango.company.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository
import site.ymango.company.entity.Company
import site.ymango.company.entity.QCompany.company

@Repository
class CompanyRepository (
    private val queryFactory: JPAQueryFactory,
    private val companyDelegateRepository: CompanyDelegateRepository,
) : CompanyDelegateRepository by companyDelegateRepository {
    fun findByName(nameKeyword: String, cursor: Int = 0, pageable: Pageable): Slice<Company> {
        queryFactory.selectFrom(company)
            .where(company.companyId.goe(cursor).and(company.name.contains(nameKeyword)))
            .limit(pageable.pageSize.toLong().plus(1L))
            .orderBy(company.name.asc())
            .fetch()
            .let {
                val content = it.take(pageable.pageSize)
                val hasNext = it.size > pageable.pageSize
                return SliceImpl(content, pageable, hasNext)
            }
    }
}