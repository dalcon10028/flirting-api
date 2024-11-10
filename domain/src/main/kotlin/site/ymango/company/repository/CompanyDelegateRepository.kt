package site.ymango.company.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.company.entity.Company

interface CompanyDelegateRepository : JpaRepository<Company, Int> {
    fun findByDomain(domain: String): Company?

    fun existsByDomain(domain: String): Boolean
}