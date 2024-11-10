package site.ymango.chat_room.v1.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SendMessageRequest(
    @field:NotBlank
    val message: String,

    @field:NotNull
    val receiverProfileId: Long?,
) {
    init {
        require(receiverProfileId != null)
    }
}
