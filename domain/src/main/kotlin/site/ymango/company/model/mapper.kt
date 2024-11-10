package site.ymango.company.model

import site.ymango.company.entity.Company

fun CompanyModel.toEntity() = Company(
    companyId = companyId,
    name = name,
    domain = domain,
    iconUrl = iconUrl,
    deletedAt = deletedAt,
)

fun Company.toModel() = CompanyModel(
    companyId = companyId,
    name = name,
    domain = domain,
    iconUrl = iconUrl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    updatedBy = updatedBy,
    deletedAt = deletedAt,
)