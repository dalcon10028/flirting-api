package site.ymango.match.model

import java.time.LocalDateTime

data class MatchCompleteEvent(
    val matchRequestId: Long,
    val requesterId: Long,
    val requesteeId: Long,
    val matchedAt: LocalDateTime,
)
