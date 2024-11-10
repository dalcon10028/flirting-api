package site.ymango.company.v1

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.Length
import org.springframework.data.domain.*
import org.springframework.web.bind.annotation.*
import site.ymango.company.v1.dto.*
import site.ymango.company.CompanyService
import org.springframework.data.web.PageableDefault
import org.springframework.validation.annotation.Validated

@Validated
@RestController
@RequestMapping("/v1/companies")
class CompanyController(
    private val companyService: CompanyService
) {
    @GetMapping
    fun getCompanies(
        @Length(max = 30) @RequestParam name: String = "",
        @Min(0) @RequestParam cursor: Int = 0,
        @PageableDefault(page = 0, size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable): Slice<CompanyResponse> =
            companyService.getCompanies(name, cursor, pageable).map { it.toResponse() }

    @PostMapping
    fun createCompany(@Valid @RequestBody company: CompanyRequest): CompanyResponse = companyService.createCompany(company.toModel()).toResponse()
}