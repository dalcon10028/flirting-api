package site.ymango.company.model

import java.time.LocalDateTime

data class CompanyModel(
    val companyId: Int? = null,
    val name: String,
    val domain: String,
    val iconUrl: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val updatedBy: String? = null,
    val deletedAt: LocalDateTime? = null,
)
