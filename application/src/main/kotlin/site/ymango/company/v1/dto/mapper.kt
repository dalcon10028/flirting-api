package site.ymango.company.v1.dto


import site.ymango.company.model.CompanyModel
import java.lang.RuntimeException

fun CompanyModel.toResponse() = CompanyResponse(
    companyId = companyId ?: throw RuntimeException("companyId is null"),
    name = name,
    domain = domain,
    iconUrl = iconUrl,
)

fun CompanyRequest.toModel() = CompanyModel(
    name = name,
    domain = domain,
)