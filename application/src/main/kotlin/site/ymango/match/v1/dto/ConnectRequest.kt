package site.ymango.match.v1.dto

import jakarta.validation.constraints.NotNull

data class ConnectRequest(
    @field:NotNull
    val requesterId: Long
)
