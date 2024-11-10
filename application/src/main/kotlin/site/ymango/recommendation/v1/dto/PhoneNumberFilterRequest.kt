package site.ymango.recommendation.v1.dto

import jakarta.validation.constraints.NotEmpty

data class PhoneNumberFilterRequest(
    @field:NotEmpty
    val phoneNumbers: List<String>,
)
