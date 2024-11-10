package site.ymango.chat_room.model

data class ChatRoomCreationEvent(
    val matchRequestId: Long,
    val requesterId: Long,
    val requesteeId: Long,
)
