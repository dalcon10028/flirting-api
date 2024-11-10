package site.ymango.company.v1.dto

data class CompanyResponse(
    val companyId: Int,
    val name: String,
    val domain: String,
    val iconUrl: String?,
)