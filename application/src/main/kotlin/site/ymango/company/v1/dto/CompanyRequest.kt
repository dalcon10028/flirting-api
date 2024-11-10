package site.ymango.company.v1.dto

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.*

data class CompanyRequest(
    @field:NotEmpty
    @field:Length(max = 40, message = "길이가 40이하여야 합니다")
    val name: String,

    @field:NotEmpty
    @field:Pattern(regexp = "^[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})\$", message = "유효한 도메인을 입력해주세요")
    val domain: String,
)