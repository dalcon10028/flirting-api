package site.ymango.chat_room.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import site.ymango.chat_room.entity.ChatRoom

interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
    fun findByUserId(userId: Long): List<ChatRoom>
    fun existsByUserIdAndOpponentUserId(userId: Long, opponentUserId: Long): Boolean
    fun findByUserIdAndOpponentUserId(userId: Long, opponentUserId: Long): ChatRoom?
    fun findByChatRoomIdAndUserId(chatRoomId: Long, userId: Long): ChatRoom?
}