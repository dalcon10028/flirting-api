package site.ymango.chat_room.model

import site.ymango.chat_room.entity.ChatRoom
import site.ymango.user.model.UserProfileModel
import java.time.LocalDateTime

data class ChatRoomModel(
    val chatRoomId: Long? = null,
    var matchRequestId: Long,
    var userId: Long,
    var opponentUserId: Long,
    var openedAt: LocalDateTime? = null,
    var closedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val opponentUserProfile: UserProfileModel, // 상대 프로필
    val opponentFcmToken: String?, // 상대 FCM 토큰
) {
    companion object {
        fun of(chatRoom: ChatRoom, profile: UserProfileModel, fcmToken: String?): ChatRoomModel {
            return ChatRoomModel(
                chatRoomId = chatRoom.chatRoomId,
                matchRequestId = chatRoom.matchRequestId,
                userId = chatRoom.userId,
                opponentUserId = chatRoom.opponentUserId,
                openedAt = chatRoom.openedAt,
                closedAt = chatRoom.closedAt,
                opponentUserProfile = profile,
                opponentFcmToken = fcmToken,
                createdAt = chatRoom.createdAt,
            )
        }
    }
}
