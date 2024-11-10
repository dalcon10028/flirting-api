package site.ymango.chat_room.v1.dto

import site.ymango.chat_room.model.ChatRoomModel
import site.ymango.user.v1.dto.UserProfileResponse
import java.time.LocalDateTime

data class ChatRoomResponse(
    val chatRoomId: Long? = null,
    var matchRequestId: Long,
    val userId: Long,
    var openedAt: LocalDateTime? = null,
    var closedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime,
    val opponentUserProfile: UserProfileResponse, // 상대 프로필
    val opponentFcmToken: String?, // 상대 FCM 토큰
) {
    companion object {
        fun of(chatRoom: ChatRoomModel): ChatRoomResponse {
            assert(chatRoom.chatRoomId != null)
            return ChatRoomResponse(
                chatRoomId = chatRoom.chatRoomId,
                matchRequestId = chatRoom.matchRequestId,
                userId = chatRoom.userId,
                openedAt = chatRoom.openedAt,
                closedAt = chatRoom.closedAt,
                opponentUserProfile = UserProfileResponse.from(chatRoom.opponentUserProfile),
                opponentFcmToken = chatRoom.opponentFcmToken,
                createdAt = chatRoom.createdAt!!,
            )
        }
    }
}
